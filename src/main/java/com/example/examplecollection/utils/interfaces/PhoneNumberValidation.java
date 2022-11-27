package com.example.examplecollection.utils.interfaces;

import javax.validation.*;

import java.lang.annotation.*;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberValidation {
	String message() default "휴대폰번호 형식 오류입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
