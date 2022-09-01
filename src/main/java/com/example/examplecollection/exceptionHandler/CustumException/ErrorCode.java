package com.example.examplecollection.exceptionHandler.CustumException;

import lombok.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //찾을 수 없습니다.
    NOT_FIND_USER(4040, 500, "유저를 찾을 수 없습니다.");

    private final int statusCode;
    private final int realStatusCode;
    private final String message;
}
