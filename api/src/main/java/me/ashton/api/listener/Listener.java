package me.ashton.api.listener;

import com.corundumstudio.socketio.SocketIOClient;

public interface Listener<T> {

    void execute(SocketIOClient client, T data);

}