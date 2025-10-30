package com.rummirimkub.game;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data // Lombok
public class GameRoom {
    private String roomId;
    private String title; // 방 제목
    private String password;
    private Boolean privateRoom;
    private List<String> players; // 참가자 유저 ID 목록
//    private GameState gameState; // (나중에 추가) 현재 게임 상태
    private int maxPlayers = 4; // 최대 인원

    public GameRoom() {
        this.roomId = UUID.randomUUID().toString();
        this.title = title;
        this.players = new ArrayList<>();
    }

    // (플레이어 추가, 삭제, 방이 꽉 찼는지 확인하는 로직 등...)
}