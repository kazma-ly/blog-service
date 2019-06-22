package com.kazma233.blog.entity.dto;

import lombok.Data;

/**
 * @author kazma
 */
@Data
public class SockMessage {

    private String online;
    private String id;
    private String name;
    private String message;
    /**
     * 1 系统 2 用户
     */
    private String type = "2";

}
