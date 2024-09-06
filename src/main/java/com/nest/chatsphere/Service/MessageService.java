package com.nest.chatsphere.Service;

import com.nest.chatsphere.entity.Message;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MessageService {

    @Value("${code.delete.all}")
    private String deleteCode;

    @Getter
    private List<Message> messages = new ArrayList<>();

    public void addMessages(String message, int requestHash) {
        if(message.startsWith("/")){
            codedMessage(message);
        }else {
            Message newMessage = new Message(message,getCurrentTimestamp(),String.valueOf(requestHash));
            this.messages.add(newMessage);
        }
    }

    private void codedMessage(String code) {
        if(code.equals(deleteCode)){
            deleteAllMesages();
        }
    }

    public static String getCurrentTimestamp() {
    	log.debug("Inside getCurrentTimeStamp");
        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
                .withZone(ZoneOffset.UTC);
        String date = formatter.format(now);
        return date;
    }

    public List<Message> getEmptyMessages() {
        Message emptyMessage = new Message("No messages yet.",getCurrentTimestamp(),String.valueOf("Chatbox".hashCode()));
        return Collections.singletonList(emptyMessage);
    }

    public int getHashCode(HttpServletRequest request) {
        int hashcode = (request.getHeader("User-Agent")+request.getRemoteAddr()).hashCode();
        return hashcode;
    }

    public boolean deleteAllMesages() {
        messages = new ArrayList<>();
        return true;
    }
}
