package com.kazma233.blog.entity.common.exception.parent;

import com.kazma233.blog.entity.result.enums.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomizeException extends RuntimeException {

    private Integer status;

    public CustomizeException(Status status, String message) {
        super(message);
        this.status = status.getStatus();
    }


    public CustomizeException(Status status) {
        super(status.getMessage());
        this.status = status.getStatus();
    }

}
