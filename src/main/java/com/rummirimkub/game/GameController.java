package com.rummirimkub.game;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/game")

public class GameController {
    @GetMapping("/rooms")
    public String rooms(Model model) {
        return "rooms";
    }
}
