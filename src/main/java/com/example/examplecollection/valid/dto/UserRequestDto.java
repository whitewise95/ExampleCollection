package com.example.examplecollection.valid.dto;

import com.example.examplecollection.valid.dto.validType.UserValidGroup;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    @Email(groups = { UserValidGroup.UserUpdate.class, UserValidGroup.UserCreate.class }, message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(groups = UserValidGroup.UserCreate.class, message = "패스워드는 공백만 있으면 안됩니다.")
    @NotBlank // @valid 를 위한 어노테이션
    private String password;

    @NotNull(groups = { UserValidGroup.UserUpdate.class, UserValidGroup.UserCreate.class }, message = "name은 null이여서는 안됩니다.")
    private String name;

    @Min(groups = UserValidGroup.UserCreate.class, value = 19, message = "나이는 19세 이상만 가능합니다.")
    @Min(value = 19)  // @valid 를 위한 어노테이션
    private int age;

    @Builder
    public UserRequestDto(String email, String password, String name, int age) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
