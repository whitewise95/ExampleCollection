package com.example.examplecollection.valid.dto;

import lombok.*;

import javax.validation.constraints.*;

public class UserRequestV2Dto {

    @Getter
    @Setter
    public static class Create {

        @Email(message = "이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "패스워드를 입력해주세요.")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @Min(value = 19, message = "19미만은 가입할 수 없습니다.")
        private int age;
    }

    @Getter
    @Setter
    public static class Update {

        @Email(message = "이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "패스워드를 입력해주세요.")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @Min(value = 19, message = "19미만은 가입할 수 없습니다.")
        private int age;
    }

    @Getter
    @Setter
    public static class Response {

        @Email(message = "이메일 형식이 아닙니다.")
        private String email;

        @NotBlank(message = "패스워드를 입력해주세요.")
        private String password;

        @NotBlank(message = "이름을 입력해주세요.")
        private String name;

        @Min(value = 19, message = "19미만은 가입할 수 없습니다.")
        private int age;
    }
}
