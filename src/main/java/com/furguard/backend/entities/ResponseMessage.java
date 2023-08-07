package com.furguard.backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Array;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
    private Integer statusCode;

    private HttpStatus status;

    private String message;
}
