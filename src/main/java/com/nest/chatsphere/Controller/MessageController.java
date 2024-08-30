package com.nest.chatsphere.Controller;

import com.nest.chatsphere.Service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/message")
@Slf4j
@CrossOrigin("**")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/getMessages")
    public ResponseEntity<?> getMessage() {
//        log.info("Getting messages");
        List<Map<String,String>> messages = messageService.getMessages();
        if(messages.isEmpty()){
//            log.info("Getting messages: No messages");
        	return ResponseEntity.ok(messageService.getEmptyMessages());
        }
        log.debug("Getting messages: "+messages);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/getHashcode")
    public ResponseEntity<?> getHashcode(HttpServletRequest request) {
        return ResponseEntity.ok(messageService.getHashCode(request));
    }

}
