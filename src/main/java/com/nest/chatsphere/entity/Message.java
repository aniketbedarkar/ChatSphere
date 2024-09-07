package com.nest.chatsphere.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Message {
    private String text;
    private String timeStamp;
    private String requestHash;
}
