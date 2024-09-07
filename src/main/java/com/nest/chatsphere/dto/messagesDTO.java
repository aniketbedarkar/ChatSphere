package com.nest.chatsphere.dto;

import com.nest.chatsphere.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class messagesDTO {
    private List<Message> messages;
    private Set<String> activeHashes;
}
