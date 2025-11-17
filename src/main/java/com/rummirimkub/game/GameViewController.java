package com.rummirimkub.game;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameViewController {

    private final GameRoomRepository gameRoomRepository;
    @GetMapping("/rooms")
    public String getRooms(Model model) {
        return "rooms";
    }

    @GetMapping("/room/{roomId}")
    public String getRoom(@PathVariable String roomId, Model model) {
        GameRoom room = gameRoomRepository.findRoomById(roomId);
        if (room.isFull()) {
            return "redirect:/game/rooms";
        }
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        room.addPlayer(username);
        model.addAttribute("room", room);
        return "waiting_room";
    }

//    @GetMapping("/play")
//    public String getRoomPlay(Model model) {
//        return "play";
//    }
}
