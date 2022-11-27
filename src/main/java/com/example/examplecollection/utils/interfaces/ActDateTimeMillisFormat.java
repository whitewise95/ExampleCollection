package com.example.examplecollection.utils.interfaces;

import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.annotation.*;

@JacksonAnnotationsInside
@Retention(RetentionPolicy.RUNTIME)
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
public @interface ActDateTimeMillisFormat {

}
