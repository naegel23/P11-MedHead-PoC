package com.medhead.api.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.REQUEST_TIMEOUT)
public class NoApiCommunication extends RuntimeException{
    public NoApiCommunication(String message) {
        super(message);
    }
}
