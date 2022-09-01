package com.example.examplecollection.exceptionHandler;

import com.example.examplecollection.exceptionHandler.CustumException.CustomException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalException {

    // 내가 만든 CustomException 클래명
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomException> customException(CustomException e) {

        //생성자 [2]에 해당
        CustomException response = new CustomException(e.getMessage(), e.getStatusCode());

        // e.getRealStatusCode() = realStatusCode에 해당되는 인자
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getRealStatusCode()));
    }
}
