package com.rummirimkub.game;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameRoomRepository {
    // 동시성 문제를 고려해 ConcurrentHashMap 사용
    private Map<String, GameRoom> roomMap = new ConcurrentHashMap<>();

    public GameRoom createRoom(String title,  String password, Integer maxPlayers) {
        GameRoom room = new GameRoom();
        room.setRoomId(UUID.randomUUID().toString());
        room.setTitle(title);

        // 비밀번호가 있는지(null이나 ""가 아닌지) 확인
        if (password != null && !password.trim().isEmpty()) {
            room.setPassword(password); // (실제로는 해시해서 저장해야 함)
            room.setPrivateRoom(true);
        } else {
            room.setPrivateRoom(false);
        }

        // 최대 인원이 null이면 기본값 4로 설정
        room.setMaxPlayers( (maxPlayers != null) ? maxPlayers : 4 );

        roomMap.put(room.getRoomId(), room);
        return room;
    }

    public GameRoom findRoomById(String roomId) {
        return roomMap.get(roomId);
    }

    public List<GameRoom> findAllRooms() {
        return new ArrayList<>(roomMap.values());
    }

    public void removeRoom(String roomId) {
        roomMap.remove(roomId);
    }
}