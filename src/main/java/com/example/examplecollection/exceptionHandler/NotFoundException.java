package com.example.examplecollection.exceptionHandler;

import com.example.examplecollection.utils.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private final String message;

    public NotFoundException() {
        this.message = ("Not found Records.");
    }

    public NotFoundException(String message) {
        this.message = message;
    }

    public NotFoundException(String message, Boolean isDefault) {
        if (Boolean.TRUE.equals(isDefault)) {
            this.message = Utils.getPostWord(message, "이", "가") + " 존재하지 않습니다.";
        } else {
            this.message = message;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

}
