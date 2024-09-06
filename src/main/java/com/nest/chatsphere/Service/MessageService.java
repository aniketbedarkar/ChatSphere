package com.nest.chatsphere.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class MessageService {

    @Value("${code.delete.all}")
    private String deleteCode;

    @Getter
    private List<Map<String,String>> messages = new ArrayList<>();

    public void addMessages(String message, int requestHash) {
        if(message.startsWith("/")){
            codedMessage(message);
        }else {
            Map<String, String> messageMap = new HashMap<>();
            messageMap.put("text", message);
            messageMap.put("timeStamp", getCurrentTimestamp());
            messageMap.put("requestHash", String.valueOf(requestHash));
            this.messages.add(messageMap);
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

    public List<Map<String,String>> getEmptyMessages() {
        Map<String,String> noMessageMap = new HashMap<>();
        noMessageMap.put("timeStamp",getCurrentTimestamp());
        noMessageMap.put("text","No messages yet.");
        noMessageMap.put("requestHash",String.valueOf("Chatbox".hashCode()));
        return Collections.singletonList(noMessageMap);
    }

    public int getHashCode(HttpServletRequest request) {
        int hashcode = (request.getHeader("User-Agent")+request.getRemoteAddr()).hashCode();
//        log.info("Getting hashcode: "+hashcode);
        return hashcode;
    }

    public boolean deleteAllMesages() {
        messages = new ArrayList<>();
        return true;
    }
}
