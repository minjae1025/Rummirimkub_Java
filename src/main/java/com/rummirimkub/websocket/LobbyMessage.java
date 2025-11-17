package com.rummirimkub.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LobbyMessage {
    private String roomId;
    private String sender;
    private String content;
}
