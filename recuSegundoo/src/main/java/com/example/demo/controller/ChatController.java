package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.ChatHandler;

@Controller
public class ChatController {

    @Autowired
    private ChatHandler chatHandler;

    @GetMapping("/")
    public String show(Model model) {
        model.addAttribute("messages", chatHandler.getMessages());
        return "chat";
    }
}