package me.ashton.api.listener.impl;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Getter;
import lombok.Setter;
import me.ashton.api.game.Game;
import me.ashton.api.listener.Listener;
import me.ashton.api.socket.SocketServer;

public class SetAdminListener implements Listener<SetAdminListener.Data> {

    @Override
    public void execute(SocketIOClient client, Data data) {
        Game game = Game.getGames().get(data.getCode());
        if (game == null) {
            System.out.println(client.getSessionId() + " trying to set admin to a game that doesnt exist?");
            return;
        }

        if (game.getAdminClient() != null) {
            System.out.println(client.getSessionId() + " trying to set admin to a game that has an admin");
            return;
        }

        game.setAdminClient(client);
        game.startUpdating();
        client.sendEvent("assignRole", "ADMIN");

        SocketServer.getDisconnectListeners().put(client.getSessionId(), () ->
                game.endGame("The admin disconnected."));

        System.out.println(data.getCode() + " has an admin");
    }

    @Getter @Setter
    public static class Data {
        private String code;
    }

}
