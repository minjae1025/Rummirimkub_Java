package com.rummirimkub.websocket;

import com.rummirimkub.game.GameRoom;
import com.rummirimkub.game.GameRoomRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LobbyController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final GameRoomRepository gameRoomRepository;

    @MessageMapping("/lobby.enter")
    public void enterUser(@Payload LobbyMessage lobbyMessage, SimpMessageHeaderAccessor headerAccessor) {
        String roomId = lobbyMessage.getRoomId();
        String username = lobbyMessage.getSender();

        headerAccessor.getSessionAttributes().put("username", username);
        headerAccessor.getSessionAttributes().put("roomId", roomId);

        GameRoom room = gameRoomRepository.findRoomById(roomId);
        if (room != null) {
            messagingTemplate.convertAndSend("/topic/room/" + roomId, room);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.JOIN);
            chatMessage.setSender(username);
            chatMessage.setRoomId(roomId);
            messagingTemplate.convertAndSend("/topic/room/" + roomId + "/chat", chatMessage);
        }
    }

    @MessageMapping("/lobby.leave")
    public void leaveUser(@Payload LobbyMessage lobbyMessage, SimpMessageHeaderAccessor headerAccessor) {
        String roomId = lobbyMessage.getRoomId();
        String username = lobbyMessage.getSender();

        GameRoom room = gameRoomRepository.findRoomById(roomId);
        if (room == null) {
            return;
        }

        // Check if the leaving user is the host
        if (room.getHost() != null && room.getHost().getUsername().equals(username)) {
            // Host is leaving, close the room for everyone
            messagingTemplate.convertAndSend("/topic/room/" + roomId + "/close", "HOST_LEFT");
            gameRoomRepository.removeRoom(roomId);
        } else {
            // A regular player is leaving
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);
            chatMessage.setRoomId(roomId);
            messagingTemplate.convertAndSend("/topic/room/" + roomId + "/chat", chatMessage);

            room.removePlayer(username);
            if (room.getPlayers().isEmpty()) {
                gameRoomRepository.removeRoom(roomId);
            } else {
                messagingTemplate.convertAndSend("/topic/room/" + roomId, room);
            }
        }
    }

    @MessageMapping("/lobby.ready")
    public void toggleReady(@Payload LobbyMessage lobbyMessage) {
        String roomId = lobbyMessage.getRoomId();
        String username = lobbyMessage.getSender();
        boolean isReady = Boolean.parseBoolean(lobbyMessage.getContent());

        GameRoom room = gameRoomRepository.findRoomById(roomId);
        if (room != null) {
            room.setPlayerReady(username, isReady);
            messagingTemplate.convertAndSend("/topic/room/" + roomId, room);
        }
    }

    @MessageMapping("/lobby.start")
    public void startGame(@Payload LobbyMessage lobbyMessage) {
        String roomId = lobbyMessage.getRoomId();
        String username = lobbyMessage.getSender();

        GameRoom room = gameRoomRepository.findRoomById(roomId);
        // 방장만 게임을 시작할 수 있고, 모든 플레이어가 준비 상태여야 함
        if (room != null && room.getHost().getUsername().equals(username) && room.areAllPlayersReady()) {
            messagingTemplate.convertAndSend("/topic/room/" + roomId + "/start", "START");
        }
    }
}
