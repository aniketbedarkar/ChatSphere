package com.nest.chatsphere.Controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {
    private final List<String> messages = new ArrayList<>();

    @GetMapping
    public String loadHome(){
        return "index";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String message, Model model) {
        if (!message.trim().isEmpty()) {
            messages.add(message);
        }
        model.addAttribute("messages", messages);
        return "index";
    }

}
