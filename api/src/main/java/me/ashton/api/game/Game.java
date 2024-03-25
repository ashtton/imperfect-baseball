package me.ashton.api.game;


import com.corundumstudio.socketio.SocketIOClient;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.ashton.api.game.config.GameConfig;
import me.ashton.api.game.data.GameData;
import me.ashton.api.game.defense.PitchType;
import me.ashton.api.game.offense.SwingTiming;
import me.ashton.api.game.result.BattingResult;
import me.ashton.api.listener.impl.DefensiveDecisionListener;
import me.ashton.api.listener.impl.OffensiveDecisionListener;
import me.ashton.api.user.User;

import java.util.*;
import java.util.stream.Collectors;

@Getter @Setter
@JsonIgnoreProperties("adminClient")
public class Game {

    @Getter private static final Map<String, Game> games = new HashMap<>();
    private static final Gson gson = new GsonBuilder().create();
    private static final Random random = new Random();

    private String gameCode;

    private transient SocketIOClient adminClient;

    private User awayTeam;

    private User homeTeam;

    private GameConfig gameConfig = new GameConfig();
    private GameData gameData = new GameData();

    private OffensiveDecisionListener.Data offensiveDecision;
    private DefensiveDecisionListener.Data defensiveDecision;

    public void playOut() {
        PitchType pitchType = defensiveDecision.getPitchType();
        SwingTiming swingTiming = offensiveDecision.getSwingTiming();

        // timing based on off-speed and fastballs
        boolean correctTiming = (swingTiming == SwingTiming.EARLY && pitchType == PitchType.FASTBALL) ||

                (swingTiming == SwingTiming.LATE && (pitchType == PitchType.CHANGEUP ||
                        pitchType == PitchType.CURVEBALL));

        boolean strike = false;

        // set what they want if answered correctly
        if (defensiveDecision.isAnsweredCorrectly()) {
            strike = defensiveDecision.isStrike();
        }

        // 50/50 chance its a strike if not answered correctly
        if (!defensiveDecision.isAnsweredCorrectly()) {
            strike = random.nextInt(100) > 50;
        }

        // if its not a strike but the offense is taking and missed the question
        // and the defense got it right then make it a strike
        if (defensiveDecision.isAnsweredCorrectly() && !offensiveDecision.isAnsweredCorrectly() && swingTiming == SwingTiming.TAKE) {
            strike = true;
        }

        // handle the result if the batter takes the pitch
        if (swingTiming == SwingTiming.TAKE) {
            if (strike) {
                handleStrike();
                handleResult(BattingResult.TOOK_STRIKE);
                return;
            }

            gameData.setBalls(gameData.getBalls() + 1);
            if (gameData.getBalls() == 4) {
                createRunner(1);
                handleResult(BattingResult.BASE_ON_BALLS);
            }

            handleResult(BattingResult.TOOK_BALL);
            return;
        }

        // handle swing if defense is correct and offense is incorreect (auto strike/out)
        if (!offensiveDecision.isAnsweredCorrectly() && defensiveDecision.isAnsweredCorrectly()) {
            if (correctTiming) {
                // luckily got a hit
                if (random.nextInt(100) > 90) {
                    createRunner(1);
                    handleResult(BattingResult.SINGLE);
                    return;
                }

                // 50/50 chance to be a flyout or groundout
                if (random.nextInt(100) > 50) {
                    handleResult(BattingResult.FLYOUT);
                    handleOut();
                    return;
                }

                handleResult(BattingResult.GROUND_OUT);
                handleOut();
                return;
            }

            // swing and miss
            handleStrike();
            handleResult(BattingResult.SWING_AND_MISS);
            return;
        }

        // handle swing if offense is correct and defense is incorrect (auto hit)
        if (offensiveDecision.isAnsweredCorrectly() && !defensiveDecision.isAnsweredCorrectly()) {
            if (correctTiming) {
                // 10% chance of being a homerun
                if (random.nextInt(100) > 80) {
                    createRunner(4);
                    handleResult(BattingResult.HOME_RUN);
                    return;
                }

                // 30% chance of being a triple
                if (random.nextInt(100) > 70) {
                    createRunner(3);
                    handleResult(BattingResult.TRIPLE);
                    return;
                }

                // 40% chance of being a double
                if (random.nextInt(100) > 60) {
                    createRunner(2);
                    handleResult(BattingResult.DOUBLE);
                    return;
                }

                // otherwise its a single
                createRunner(1);
                handleResult(BattingResult.SINGLE);
                return;
            }

            // very small chance for flyout
            if (random.nextInt(100) > 85) {
                handleResult(BattingResult.FLYOUT);
                handleOut();
                return;
            }

            createRunner(1);
            handleResult(BattingResult.SINGLE);
            return;
        }

        // handle swing if both are correct

        // correct timing
        if (correctTiming) {
            // 65% chance of being a hit
            if (random.nextInt(100) > 65) {

                // 10% chance of a homerun
                if (random.nextInt(100) > 90) {
                    createRunner(4);
                    handleResult(BattingResult.HOME_RUN);
                    return;
                }

                // 15% chance of a triple
                if (random.nextInt(100) > 85) {
                    createRunner(3);
                    handleResult(BattingResult.TRIPLE);
                    return;
                }

                // 20% chance of a double
                if (random.nextInt(100) > 80) {
                    createRunner(2);
                    handleResult(BattingResult.DOUBLE);
                    return;
                }

                // otherwise its a single
                createRunner(1);
                handleResult(BattingResult.SINGLE);
                return;
            }

            // otherwise its a flyout
            handleResult(BattingResult.FLYOUT);
            handleOut();
            return;
        }

        // incorrect timing

        // chance of swing and miss
        if (random.nextInt(100) > 50) {
            handleResult(BattingResult.SWING_AND_MISS);
            handleStrike();
            return;
        }

        // small chance of getting a single
        if (random.nextInt(100) > 85) {
            handleResult(BattingResult.SINGLE);
            createRunner(1);
            return;
        }

        // otherwise its either a flyout or groundout

        if (random.nextInt(100) > 50) {
            handleResult(BattingResult.FLYOUT);
            handleOut();
            return;
        }

        handleResult(BattingResult.GROUND_OUT);
        handleOut();
    }

    public void createRunner(int bases) {
        gameData.setStrikes(0);
        gameData.setBalls(0);
    }

    public void handleStrike() {
        gameData.setStrikes(gameData.getStrikes() + 1);
        if (gameData.getStrikes() == 3) {
            handleOut();
            handleResult(BattingResult.STRIKEOUT);
        }
    }

    public void handleOut() {
        gameData.setOuts(gameData.getOuts() + 1);
        gameData.setStrikes(0);
        gameData.setBalls(0);

        if (gameData.getOuts() < 3) {
            return;
        }

        gameData.setOuts(0);

        if (gameData.isBottomInning()) {
            gameData.setBottomInning(false);
            gameData.setInning(gameData.getInning() + 1);
            if (gameConfig.getInnings() < gameData.getInning() &&
                awayTeam.getScore() != homeTeam.getScore()) {
                endGame();
            }
            return;
        }

        gameData.setBottomInning(true);
        if (gameData.getInning() == gameConfig.getInnings() &&
            homeTeam.getScore() > awayTeam.getScore()) {
            endGame();
        }
    }

    public void handleResult(BattingResult result) {

    }

    public void endGame() {

    }

    public void startUpdating() {
        new Thread() {
            @SneakyThrows
            public void run() {
                while(true) {
                    if (!Game.getGames().containsKey(gameCode)) {
                        stop();
                        return;
                    }

                    getClients().forEach(client -> client.sendEvent("updateGame", gson.toJson(Game.getGames().get(gameCode))));
                    Thread.sleep(250);
                }
            }
        }.start();
    }

    public List<SocketIOClient> getClients() {
        List<SocketIOClient> clients = new ArrayList<>(Collections.singletonList(adminClient));

        if (awayTeam != null) {
            clients.add(awayTeam.getSocket());
        }

        if (homeTeam != null) {
            clients.add(homeTeam.getSocket());
        }

        return clients.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
