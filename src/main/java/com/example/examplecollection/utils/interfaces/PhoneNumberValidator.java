package com.example.examplecollection.utils.interfaces;

import com.example.examplecollection.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.*;

import java.util.regex.*;

@Slf4j
public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValidation, String> {

	@Override
	public void initialize(PhoneNumberValidation constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}

	@Override
	public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
		if(Utils.isEmpty(phoneNumber))
			return true;
		Pattern pattern = Pattern.compile("^((010\\d{4})|(01[1|6|7|8|9]\\d{3,4}))(\\d{4})$");
		Matcher matcher = pattern.matcher(phoneNumber);
		return matcher.matches();
	}
}
