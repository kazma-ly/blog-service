package com.kazma233.blog.utils;

import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.exception.ValidatedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidationUtils {

    public static void checkFieldErrors(BindingResult bindingResult) throws ValidatedException {

        if (!bindingResult.hasErrors()) {
            return;
        }

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        fieldErrors.forEach(
                fieldError -> stringBuilder.append("[").append(fieldError.getDefaultMessage()).append("] ")
        );

        throw new ValidatedException(Status.ARG_ERROR, stringBuilder.toString());
    }
}