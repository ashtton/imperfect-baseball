package me.ashton.api.socket;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.Getter;
import me.ashton.api.listener.impl.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class SocketServer {

    @Getter private static final Map<UUID, Runnable> disconnectListeners = new HashMap<>();

    private final SocketIOServer server;

    public SocketServer(SocketIOServer server) {
        this.server = server;
        server.start();

        JoinRoomListener joinRoomListener = new JoinRoomListener();
        SetAdminListener setAdminListener = new SetAdminListener();
        NextPitchListener nextPitchListener = new NextPitchListener();
        StartGameListener startGameListener = new StartGameListener();
        OffensiveDecisionListener offensiveDecisionListener = new OffensiveDecisionListener();
        DefensiveDecisionListener defensiveDecisionListener = new DefensiveDecisionListener();

        server.addEventListener("joinRoom", JoinRoomListener.Data.class, ((client, data, ackSender) ->
                joinRoomListener.execute(client, data)));

        server.addEventListener("setAdmin", SetAdminListener.Data.class, ((client, data, ackSender) ->
                setAdminListener.execute(client, data)));

        server.addEventListener("nextPitch", NextPitchListener.Data.class, ((client, data, ackSender) ->
                nextPitchListener.execute(client, data)));

        server.addEventListener("startGame", StartGameListener.Data.class, ((client, data, ackSender) ->
                startGameListener.execute(client, data)));

        server.addEventListener("setOffense", OffensiveDecisionListener.Data.class, ((client, data, ackSender) ->
                offensiveDecisionListener.execute(client, data)));

        server.addEventListener("setDefense", DefensiveDecisionListener.Data.class, ((client, data, ackSender) ->
                defensiveDecisionListener.execute(client, data)));

        server.addDisconnectListener(client -> {
            if (!disconnectListeners.containsKey(client.getSessionId())) {
                return;
            }

            disconnectListeners.get(client.getSessionId()).run();
        });
    }

}
