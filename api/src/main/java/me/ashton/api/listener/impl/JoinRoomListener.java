package me.ashton.api.listener.impl;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Getter;
import lombok.Setter;
import me.ashton.api.game.Game;
import me.ashton.api.listener.Listener;
import me.ashton.api.socket.SocketServer;
import me.ashton.api.user.User;

public class JoinRoomListener implements Listener<JoinRoomListener.Data> {

    @Override
    public void execute(SocketIOClient client, Data data) {
        Game game = Game.getGames().get(data.getCode());
        if (game == null) {
            System.out.println(data.getUsername() + " trying to join game that doesnt exist?");
            return;
        }

        if (game.getHomeTeam() != null && game.getAwayTeam() != null) {
            System.out.println(data.getUsername() + " trying to join game that is full");
            return;
        }

        User user = new User(data.getUsername(), client);

        if (game.getHomeTeam() == null) {
            game.setHomeTeam(user);
            client.sendEvent("assignRole", "HOME");
        } else {
            game.setAwayTeam(user);
            client.sendEvent("assignRole", "AWAY");
        }

        SocketServer.getDisconnectListeners().put(client.getSessionId(), () ->
                game.endGame(data.getUsername() + " disconnected."));

        System.out.println(data.getUsername() + " is joining " + data.getCode());
    }

    @Getter @Setter
    public static class Data {
        private String code;
        private String username;
    }

}
