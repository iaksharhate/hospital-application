package com.example.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {

    public String message;
    public Object data;

    public Response(String message, Object data) {
        this.message = message;
        this.data = data;
    }
}
