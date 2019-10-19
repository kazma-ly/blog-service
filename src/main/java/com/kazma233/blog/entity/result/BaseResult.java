package com.kazma233.blog.entity.result;

import com.kazma233.blog.entity.result.enums.Status;
import com.kazma233.blog.entity.common.exception.parent.CustomizeException;
import lombok.Data;

@Data
public class BaseResult<T> {

    private Integer status;
    private Boolean success;
    private String message;
    private T result;

    public static <T> BaseResult<T> success() {
        return success(Status.SUCCESS, null);
    }

    public static <T> BaseResult<T> success(T result) {
        return success(Status.SUCCESS, result);
    }

    public static <T> BaseResult<T> success(Status resultEnums, T result) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setStatus(resultEnums.getStatus());
        baseResult.setMessage(resultEnums.getMessage());
        baseResult.setSuccess(true);
        baseResult.setResult(result);

        return baseResult;
    }

    public static <T> BaseResult<T> failed(Status resultEnums) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setStatus(resultEnums.getStatus());
        baseResult.setMessage(resultEnums.getMessage());
        baseResult.setSuccess(true);

        return baseResult;
    }

    public static <T> BaseResult<T> failed(Status resultEnums, String message) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setStatus(resultEnums.getStatus());
        baseResult.setMessage(message);
        baseResult.setSuccess(true);

        return baseResult;
    }

    public static <T> BaseResult<T> failed(CustomizeException e) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setStatus(e.getStatus());
        baseResult.setMessage(e.getMessage());
        baseResult.setSuccess(true);

        return baseResult;
    }

}
