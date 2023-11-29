package com.revamp.springdal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class FileNoContentException extends Exception{
    private static final long serialVersionUID = 1L;

    public FileNoContentException(String message){
        super(message);
    }
}
