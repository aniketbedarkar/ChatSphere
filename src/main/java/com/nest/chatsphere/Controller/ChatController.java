package com.nest.chatsphere.Controller;

import com.nest.chatsphere.Service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@CrossOrigin("**")
public class ChatController {

    @Autowired
    MessageService messageService;

    @GetMapping
    public String loadHome(){
        log.info("Loading home page");
        return "index";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String message, Model model) {
        log.info("Sending message");
        if (!message.trim().isEmpty()) {
            messageService.addMessages(message);
        }
        List<String> messages = messageService.getMessages();
        model.addAttribute("messages", messages);
        return "index";
    }
}
