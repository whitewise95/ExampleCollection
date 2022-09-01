package com.example.examplecollection.exceptionHandler.CustumException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException {

    private String message;  // 프론트에 전달할 message
    private int statusCode;  // 프론트에 전달할 구분할  statusCode

    @JsonIgnore
    private int realStatusCode;   // 실제 발생하는 StatusCode  프론트엔 주지 않을 것으므로 @JsonIgnore 설정

    /*
    *  throw new CustomException(ErrorCode.xxx) 하기 위한 생성자 [1]
    * */
    public CustomException(ErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.statusCode = errorCode.getStatusCode();
        this.realStatusCode = errorCode.getRealStatusCode();
    }

    /*
     *  @ExceptionHandler 를 위한 생성자  [2]
     * */
    public CustomException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
