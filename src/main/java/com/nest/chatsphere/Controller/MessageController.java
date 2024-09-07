package com.nest.chatsphere.Controller;

import com.nest.chatsphere.Service.MessageService;
import com.nest.chatsphere.dto.messagesDTO;
import com.nest.chatsphere.entity.Message;
import com.nest.chatsphere.response.ChatSphereResponse;
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

    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestParam("message") String message, HttpServletRequest request){
        int requestHash = messageService.getHashCode(request);
        if (!message.trim().isEmpty()) {
            messageService.addMessages(message, requestHash);
        }
        return ResponseEntity.ok().body(ChatSphereResponse.builder().status(ChatSphereResponse.ResponseStatus.SUCCESS).build());
    }

    @GetMapping("/getMessages")
    public ResponseEntity<messagesDTO> getMessage() {
        messagesDTO messagesDTO = messageService.getMessagesDTO();
        log.debug("Getting messages: "+messagesDTO);
        return ResponseEntity.ok(messagesDTO);
    }

    @GetMapping("/getHashcode")
    public ResponseEntity<?> getHashcode(HttpServletRequest request) {
    	log.info("getting Hashcode");
        return ResponseEntity.ok(Collections.singletonMap("hashcode",String.valueOf(messageService.getHashCode(request))));
    }

    @DeleteMapping("/allMessages")
    public ResponseEntity<?> deleteAllMessages(){
        messageService.deleteAllMesages();
        return ResponseEntity.ok("Successfully deleted all messages.");
    }

    @PostMapping("/setIAmActive")
    public ResponseEntity<?> setIAmActive(@RequestParam(value = "hashcode",required = false) String hashcode, HttpServletRequest request){
        log.info("I am active: "+hashcode);
        Boolean markedActive;
        if(hashcode!= null && !hashcode.isBlank() && !hashcode.isEmpty() && !hashcode.equalsIgnoreCase("null")) {
            markedActive = messageService.setIAmActive(hashcode);
        }else{
            markedActive = messageService.setIAmActive(String.valueOf(messageService.getHashCode(request)));
        }
        if(markedActive) {
            return ResponseEntity.ok().body(ChatSphereResponse.builder().status(ChatSphereResponse.ResponseStatus.SUCCESS).build());
        }else{
            return ResponseEntity.badRequest().body(ChatSphereResponse.builder().status(ChatSphereResponse.ResponseStatus.FAILURE).build());
        }
    }

    @PostMapping("/setIAmInActive")
    public ResponseEntity<?> setIAmInActive(@RequestParam(value = "hashcode", required = false) String hashcode, HttpServletRequest request){
        log.info("I am inactive: "+hashcode);
        Boolean markedInActive;
        if(hashcode!= null && !hashcode.isBlank() && !hashcode.isEmpty()) {
            markedInActive = messageService.setIAmInActive(hashcode);
        }else{
            markedInActive = messageService.setIAmInActive(String.valueOf(messageService.getHashCode(request)));
        }
        if(markedInActive) {
            return ResponseEntity.ok().body(ChatSphereResponse.builder().status(ChatSphereResponse.ResponseStatus.SUCCESS).build());
        }else{
            return ResponseEntity.ok().body(ChatSphereResponse.builder().status(ChatSphereResponse.ResponseStatus.FAILURE).build());
        }
    }

    @GetMapping("/getAllActiveHashcodes")
    public ResponseEntity<?> getAllActiveHashes(){
        return ResponseEntity.ok().body(ChatSphereResponse.builder().data(messageService.getAllActiveHashcodes()).status(ChatSphereResponse.ResponseStatus.SUCCESS).build());
    }
}
