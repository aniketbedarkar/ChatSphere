package com.nest.chatsphere.Controller;

import com.nest.chatsphere.Service.MessageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@CrossOrigin("**")
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    MessageService messageService;

    @GetMapping
    public String loadHome(){
        log.info("Loading home page");
        return "index";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String message, Model model, HttpServletRequest request) {
        log.info("Sending message");
        int requestHash = messageService.getHashCode(request);
        if (!message.trim().isEmpty()) {
            messageService.addMessages(message, requestHash);
        }
        List<Map<String,String>> messages = messageService.getMessages();
        model.addAttribute("messages", messages);
        return "index";
    }
}
