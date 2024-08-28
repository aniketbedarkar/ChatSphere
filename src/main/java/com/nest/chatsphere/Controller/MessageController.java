package com.nest.chatsphere.Controller;

import com.nest.chatsphere.Service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@CrossOrigin("**")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/getMessages")
    public ResponseEntity<List<String>> getMessage() {
        log.info("Getting messages");
        List<String> messages = messageService.getMessages();
        if(messages.isEmpty()){
            log.info("Getting messages: No messages");
            return ResponseEntity.ok(Collections.singletonList("No messages"));
        }
        log.info("Getting messages: "+messages);
        return ResponseEntity.ok(messages);
    }

}
