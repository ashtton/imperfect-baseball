package me.ashton.api.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import me.ashton.api.listener.impl.JoinRoomListener;
import me.ashton.api.listener.impl.SetAdminListener;
import org.springframework.stereotype.Component;

@Component
public class SocketServer {

    private final SocketIOServer server;

    public SocketServer(SocketIOServer server) {
        this.server = server;
        server.start();

        JoinRoomListener joinRoomListener = new JoinRoomListener();
        SetAdminListener setAdminListener = new SetAdminListener();

        server.addEventListener("joinRoom", JoinRoomListener.Data.class, ((client, data, ackSender) ->
                joinRoomListener.execute(client, data)));

        server.addEventListener("setAdmin", SetAdminListener.Data.class, ((client, data, ackSender) ->
                setAdminListener.execute(client, data)));
    }

}
