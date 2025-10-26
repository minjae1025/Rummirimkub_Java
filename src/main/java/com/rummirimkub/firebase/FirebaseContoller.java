package com.rummirimkub.firebase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/firebase")
public class FirebaseContoller {
    private final FirebaseService FirebaseService;

    @Autowired
    public FirebaseContoller(FirebaseService FirebaseService) {
        this.FirebaseService = FirebaseService;
    }
    
    @GetMapping("")
    public String indexFirebase() {
        return "/firebase 입니다";
    }

//    @GetMapping("/save")
//    public String testFirebase() {
//        FirebaseService.save();
//        return "Firebase function executed successfully!";
//    }
}
