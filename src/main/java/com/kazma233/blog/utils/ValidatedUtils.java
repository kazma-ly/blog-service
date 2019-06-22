package com.kazma233.blog.utils;

import com.kazma233.blog.exception.ValidatedException;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 数据校验工具
 * Created by mac_zly on 2017/4/27.
 */

public class ValidatedUtils {

    // 检查数据验证的错误
    public static void checkFieldErrors(List<FieldError> fieldErrors) throws ValidatedException {
        if (fieldErrors != null && fieldErrors.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            fieldErrors.forEach(fieldError -> {
                stringBuilder.append("[").append(fieldError.getDefaultMessage()).append("] ");
            });
            throw new ValidatedException(stringBuilder.toString(), 0);
        }
    }

}
