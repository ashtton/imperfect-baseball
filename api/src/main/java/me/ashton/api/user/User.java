package me.ashton.api.user;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@RequiredArgsConstructor
public class User {

    private final String name;

    private final transient SocketIOClient socket;

    private int score;
    private int errors;
    private int hits;

}
