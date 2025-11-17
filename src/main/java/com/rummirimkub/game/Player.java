package com.rummirimkub.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private String username;
    private boolean ready;

    public Player(String username) {
        this.username = username;
        this.ready = false;
    }
}
