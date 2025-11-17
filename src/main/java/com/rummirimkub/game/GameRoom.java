package com.rummirimkub.game;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class GameRoom {
    private String roomId;
    private String title; // 방 제목
    private String password;
    private Boolean privateRoom;
    private List<Player> players; // 참가자 객체 목록
    private int gameStart; // (나중에 추가) 현재 게임 상태
    private int maxPlayers = 4; // 최대 인원

    public GameRoom(String title) {
        this.roomId = UUID.randomUUID().toString();
        this.title = title;
        this.players = new ArrayList<>();
    }

    public void addPlayer(String username) {
        if (players.stream().noneMatch(p -> p.getUsername().equals(username))) {
            players.add(new Player(username));
        }
    }

    public void removePlayer(String username) {
        players.removeIf(p -> p.getUsername().equals(username));
    }

    public boolean isFull() {
        return players.size() >= maxPlayers;
    }

    public Player getHost() {
        if (players.isEmpty()) {
            return null;
        }
        return players.get(0);
    }

    public Player getPlayer(String username) {
        return players.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void setPlayerReady(String username, boolean ready) {
        Player player = getPlayer(username);
        if (player != null) {
            // 호스트는 준비 상태를 변경하지 않음 (항상 준비된 것으로 간주하거나, 시작 버튼으로 제어)
            if (!Objects.equals(getHost().getUsername(), username)) {
                player.setReady(ready);
            }
        }
    }

    public boolean areAllPlayersReady() {
        if (players.size() <= 1) {
            return false; // 혼자서는 게임 시작 불가
        }
        // 호스트를 제외한 모든 플레이어가 준비되었는지 확인
        return players.stream()
                .filter(p -> !Objects.equals(p.getUsername(), getHost().getUsername()))
                .allMatch(Player::isReady);
    }
}