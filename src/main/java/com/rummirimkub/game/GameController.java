package com.rummirimkub.game;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/game")
@RestController
public class GameController {

    @GetMapping("/getRooms")
    public ResponseEntity<List<GameRoom>> getRooms() {
        List<GameRoom> rooms = gameRoomRepository.findAllRooms(); // Assuming findAll() method exists in GameRoomRepository
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    private final GameRoomRepository gameRoomRepository;

    @PostMapping("/createRoom")
    public ResponseEntity<?> createRoom(@ModelAttribute CreateRoomRequest request) {
        // (로그인한 유저 정보 확인 로직, 예: @AuthenticationPrincipal)
        GameRoom newRoom = gameRoomRepository.createRoom(
                request.getTitle(),
                request.getPassword(),
                request.getMaxPlayers()
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/game/rooms"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
