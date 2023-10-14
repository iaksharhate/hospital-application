package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MasterResponse {

    public String status;
    public String code;
    public Object payload;

    public MasterResponse(String status, String code, Object payload) {
        this.status = status;
        this.code = code;
        this.payload = payload;
    }
}
