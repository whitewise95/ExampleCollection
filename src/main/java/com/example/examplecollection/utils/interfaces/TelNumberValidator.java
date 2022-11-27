package com.example.examplecollection.utils.interfaces;

import com.example.examplecollection.utils.Utils;
import lombok.extern.slf4j.Slf4j;

import javax.validation.*;

import java.util.regex.*;

@Slf4j
public class TelNumberValidator implements ConstraintValidator<TelNumberValidation, String> {

    @Override
    public void initialize(TelNumberValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String telNumber, ConstraintValidatorContext context) {
        if (Utils.isEmpty(telNumber))
            return true;
        Pattern pattern = Pattern.compile("^(0(2|3[1-3]|4[1-4]|5[1-5]|6[1-4]|70))(\\d{3,4})(\\d{4})$");
        Matcher matcher = pattern.matcher(telNumber);
        return matcher.matches();
    }
}
