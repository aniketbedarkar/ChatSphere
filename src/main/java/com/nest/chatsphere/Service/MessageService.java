package com.nest.chatsphere.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MessageService {
    private final List<String> messages = new ArrayList<>();

    public List<String> getMessages(){
        return this.messages;
    }

    public void addMessages(String message) {
        this.messages.add(message);
    }
}
