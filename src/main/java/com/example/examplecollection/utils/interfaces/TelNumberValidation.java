package com.example.examplecollection.utils.interfaces;

import javax.validation.*;

import java.lang.annotation.*;

@Constraint(validatedBy = TelNumberValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TelNumberValidation {
	String message() default "유선전화번호 형식 오류입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
