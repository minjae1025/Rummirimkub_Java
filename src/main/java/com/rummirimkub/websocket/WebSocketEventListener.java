package com.rummirimkub.websocket;

import com.rummirimkub.game.GameRoom;
import com.rummirimkub.game.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final GameRoomRepository gameRoomRepository;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        String roomId = (String) headerAccessor.getSessionAttributes().get("roomId");

        if (username != null && roomId != null) {
            GameRoom room = gameRoomRepository.findRoomById(roomId);
            if (room != null) {
                room.removePlayer(username);

                if (room.getPlayers().isEmpty()) {
                    gameRoomRepository.removeRoom(roomId);
                } else {
                    // Broadcast the updated room state to remaining players
                    messagingTemplate.convertAndSend("/topic/room/" + roomId, room);
                }
            }
        }
    }
}
