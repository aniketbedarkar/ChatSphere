package com.nest.chatsphere.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ChatSphereResponse {
    private ResponseStatus status;
    private Object data;
    public enum ResponseStatus{
        SUCCESS,
        FAILURE
    }
}
