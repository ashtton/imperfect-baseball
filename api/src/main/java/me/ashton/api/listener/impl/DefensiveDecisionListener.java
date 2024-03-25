package me.ashton.api.listener.impl;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Getter;
import lombok.Setter;
import me.ashton.api.game.Game;
import me.ashton.api.game.defense.PitchType;
import me.ashton.api.game.offense.SwingTiming;
import me.ashton.api.listener.Listener;

public class DefensiveDecisionListener implements Listener<DefensiveDecisionListener.Data> {

    @Override
    public void execute(SocketIOClient client, Data data) {
        Game game = Game.getGames().get(data.getCode());
        if (game == null) {
            System.out.println("game doesnt exist");
            return;
        }



    }

    @Getter @Setter
    public static class Data {
        private String code;
        private boolean answeredCorrectly;
        private PitchType pitchType;
        private boolean strike;
    }

}
