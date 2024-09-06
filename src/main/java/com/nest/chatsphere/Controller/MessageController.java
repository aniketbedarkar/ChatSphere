package com.nest.chatsphere.Controller;

import com.nest.chatsphere.Service.MessageService;
import com.nest.chatsphere.entity.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
@Slf4j
@CrossOrigin("**")
public class MessageController {

    @Autowired
    MessageService messageService;

    @GetMapping("/getMessages")
    public ResponseEntity<?> getMessage() {
        List<Message> messages = messageService.getMessages();
        if(messages.isEmpty()){
        	return ResponseEntity.ok(messageService.getEmptyMessages());
        }
        log.debug("Getting messages: "+messages);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/getHashcode")
    public ResponseEntity<?> getHashcode(HttpServletRequest request) {
        return ResponseEntity.ok(Collections.singletonMap("hashcode",String.valueOf(messageService.getHashCode(request))));
    }

    @DeleteMapping("/allMessages")
    public ResponseEntity<?> deleteAllMessages(){
        messageService.deleteAllMesages();
        return ResponseEntity.ok("Successfully deleted all messages.");
    }
}
