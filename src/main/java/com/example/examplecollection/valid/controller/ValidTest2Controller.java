package com.example.examplecollection.valid.controller;

import com.example.examplecollection.exceptionHandler.NotFoundException;
import com.example.examplecollection.valid.dto.UserRequestV2Dto;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/v2")
public class ValidTest2Controller {

    /**
     * NotFoundException 커스텀 테스트
     * */
    @GetMapping
    public void NotFoundExceptionTest(){
        Optional<String> MemberName = null;
        MemberName.orElseThrow(() -> new NotFoundException("이름", true)); //이름이 존재하지 않습니다.
    }

    @PostMapping
    public void saveValid(@RequestBody @Valid UserRequestV2Dto.Create create){

    }

    @PutMapping("/{id}")
    public void updateValid(@RequestBody @Valid UserRequestV2Dto.Update update, @PathVariable Long id){

    }

    @DeleteMapping("/{id}")
    public void deleteValid(@PathVariable Long id){

    }



}
