package me.ashton.api.listener.impl;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import me.ashton.api.game.Game;
import me.ashton.api.game.offense.SwingTiming;
import me.ashton.api.listener.Listener;

public class OffensiveDecisionListener implements Listener<OffensiveDecisionListener.Data> {

    @Override
    public void execute(SocketIOClient client, Data data) {
        Game game = Game.getGames().get(data.getCode());
        if (game == null) {
            System.out.println("game doesnt exist");
            return;
        }

        game.setOffensiveDecision(data);
        game.playOut();

        System.out.println("offensive decision: " + new Gson().toJson(data));
    }

    @Getter @Setter
    public static class Data {
        private String code;
        private boolean answeredCorrectly;
        private SwingTiming swingTiming;
    }

}
