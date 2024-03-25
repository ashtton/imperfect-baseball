package me.ashton.api.game.data;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class GameData {
    private int inning = 1;
    private boolean bottomInning;

    private int outs;
    private int strikes;
    private int balls;

    private Map<Integer, Boolean> runners = new HashMap<>();
}
