package com.example.project.first.QuickPay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WakeupController {
    @GetMapping("/wakeup")
    public ResponseEntity<String> wakeServerUp(){
        String msg =  "I am Working Now";
        return  new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
