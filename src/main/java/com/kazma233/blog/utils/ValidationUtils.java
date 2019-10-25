package com.kazma233.blog.utils;

import com.kazma233.blog.entity.common.enums.Status;
import com.kazma233.blog.entity.common.exception.ValidatedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidationUtils {

    public static void checkFieldErrors(BindingResult bindingResult) throws ValidatedException {

        if (!bindingResult.hasErrors()) {
            return;
        }

        throw new ValidatedException(Status.ARGS_ERROR, getErrorMessage(bindingResult));
    }

    public static String getErrorMessage(BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder errorStringBuilder = new StringBuilder();
        fieldErrors.forEach(
                fieldError -> errorStringBuilder.append("[").append(fieldError.getDefaultMessage()).append("] ")
        );

        return errorStringBuilder.toString();
    }

}