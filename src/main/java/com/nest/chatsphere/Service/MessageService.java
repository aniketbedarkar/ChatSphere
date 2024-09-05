package com.nest.chatsphere.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class MessageService {
    private final List<Map<String,String>> messages = new ArrayList<>();

    public List<Map<String,String>> getMessages(){
        return this.messages;
    }

    public void addMessages(String message, int requestHash) {
        Map<String,String> messageMap = new HashMap<>();
        messageMap.put("text",message);
        messageMap.put("timeStamp", getCurrentTimestamp());
        messageMap.put("requestHash", String.valueOf(requestHash));
        this.messages.add(messageMap);
    }
    public static String getCurrentTimestamp() {
    	log.debug("Inside getCurrentTimeStamp");
        Instant now = Instant.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
                .withZone(ZoneOffset.UTC);
        String date = formatter.format(now);
        return date;
    }

    public List<Map<String,String>> getEmptyMessages() {
        Map<String,String> noMessageMap = new HashMap<>();
        noMessageMap.put("timeStamp",getCurrentTimestamp());
        noMessageMap.put("text","No messages yet.");
        return Collections.singletonList(noMessageMap);
    }

    public int getHashCode(HttpServletRequest request) {
        int hashcode = (request.getHeader("User-Agent")+request.getRemoteAddr()).hashCode();
//        log.info("Getting hashcode: "+hashcode);
        return hashcode;
    }
}
