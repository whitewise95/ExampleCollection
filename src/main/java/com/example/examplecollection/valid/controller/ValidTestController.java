package com.example.examplecollection.valid.controller;

import com.example.examplecollection.valid.dto.UserRequestDto;
import com.example.examplecollection.valid.dto.validType.UserValidGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ValidTestController {

    /*
     * @Valid를 이용한 검증 예시
     * 이메일: 이메일 형식으로 입력을 받아야 한다.
     * 비밀번호: 빈 값을 받으면 안된다.
     * 이름: 사용자의 이름은 null이면 안된다.
     * 나이: 사용자의 나이는 19세 이상이여야 한다.
     * */
    @PostMapping("/validTest")
    public void validTest(@RequestBody @Valid UserRequestDto userRequestDto) {
        System.out.println("로직 수행");
        System.out.println(userRequestDto);
    }

    /*
     *  @Validated를 이용해 그룹화하여 검증
     * 이메일: 이메일 형식으로 입력을 받아야 한다.
     * 이름: 사용자의 이름은 null이면 안된다.
     * */
    @PostMapping("/userUpdateTest")
    public void UserNameUpdate(
            @RequestBody
            @Validated(UserValidGroup.UserUpdate.class) UserRequestDto userRequestDto) {
        System.out.println("로직 수행 중");
        System.out.println("변경 완료");
    }

    /*
     *  @Validated를 이용해 그룹화하여 검증
     * 이메일: 이메일 형식으로 입력을 받아야 한다.
     * 비밀번호: 빈 값을 받으면 안된다.
     * 이름: 사용자의 이름은 null이면 안된다.
     * 나이: 사용자의 나이는 19세 이상이여야 한다.
     * */
    @PostMapping("/userCreatTest")
    public void UserCreate(
            @RequestBody
            @Validated(UserValidGroup.UserCreate.class) UserRequestDto userRequestDto) {
        System.out.println("로직 수행 중");
        System.out.println("유저 생성");
    }
}
