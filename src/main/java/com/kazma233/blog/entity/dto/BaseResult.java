package com.kazma233.blog.entity.dto;

import com.kazma233.blog.enums.ResultEnums;

/**
 * http响应
 * Created by mac_zly on 2017/3/22.
 */
public class BaseResult<T> {

    public static final Integer SUCCESS = 1;
    public static final Integer FAIL = 0;
    public static final Integer ERROR = -1;

    private Integer status;
    private Boolean success;
    private String message;
    private T result;

    public BaseResult() {
    }

    /**
     * 创建一个Http请求成功的响应对象
     *
     * @param resultEnums 返回对象枚举
     * @param result      返回的数据
     * @param <T>         泛型
     * @return 响应对象
     */
    public static <T> BaseResult<T> createSuccessResult(ResultEnums resultEnums, T result) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setStatus(resultEnums.getStatus());
        baseResult.setMessage(resultEnums.getMessage());
        baseResult.setSuccess(true);
        baseResult.setResult(result);
        return baseResult;
    }

    /**
     * 创建一个Http请求失败的响应对象
     *
     * @param resultEnums 返回对象枚举
     * @param <T>         泛型
     * @return 响应对象
     */
    public static <T> BaseResult<T> createFailResult(ResultEnums resultEnums) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setStatus(resultEnums.getStatus());
        baseResult.setMessage(resultEnums.getMessage());
        baseResult.setSuccess(true);
        baseResult.setResult(null);
        return baseResult;
    }

    public static <T> BaseResult<T> createResult(Integer status, String message, Boolean success, T result) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setStatus(status);
        baseResult.setSuccess(success);
        baseResult.setMessage(message);
        baseResult.setResult(result);
        return baseResult;
    }

    public static <T> BaseResult<T> createResult(Integer status, String message, Boolean success) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setStatus(status);
        baseResult.setSuccess(success);
        baseResult.setMessage(message);
        return baseResult;
    }

    public static <T> BaseResult<T> createResult(Integer status) {
        BaseResult<T> baseResult = new BaseResult<>();
        baseResult.setStatus(status);
        if (status == 1) {
            baseResult.setSuccess(true);
            baseResult.setMessage("请求成功");
        } else {
            baseResult.setSuccess(false);
            baseResult.setMessage("请求失败");
        }
        return baseResult;
    }

    public static <T> BaseResult<T> createResult(Integer status, T data) {
        BaseResult<T> baseResult = createResult(status);
        baseResult.setResult(data);
        return baseResult;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
