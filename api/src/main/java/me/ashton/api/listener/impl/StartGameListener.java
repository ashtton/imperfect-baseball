package me.ashton.api.listener.impl;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Getter;
import lombok.Setter;
import me.ashton.api.game.Game;
import me.ashton.api.game.offense.SwingTiming;
import me.ashton.api.listener.Listener;

public class StartGameListener implements Listener<StartGameListener.Data> {

    @Override
    public void execute(SocketIOClient client, Data data) {
        Game game = Game.getGames().get(data.getCode());
        if (game == null) {
            System.out.println("game doesnt exist");
            return;
        }

        game.getClients().forEach(socket -> socket.sendEvent("startGame"));
    }

    @Getter @Setter
    public static class Data {
        private String code;
    }

}
