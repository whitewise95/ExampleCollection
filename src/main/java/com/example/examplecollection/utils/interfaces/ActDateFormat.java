package com.example.examplecollection.utils.interfaces;

import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.annotation.*;

@JacksonAnnotationsInside
@Retention(RetentionPolicy.RUNTIME)
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
@DateTimeFormat(pattern = "yyyy-MM-dd")
public @interface ActDateFormat {

}
