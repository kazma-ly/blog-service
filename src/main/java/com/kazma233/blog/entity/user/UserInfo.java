package com.kazma233.blog.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

    private String id;
    private String uid;
    private String noticeEmail;
    private String nickname;
    private String noticeStatus;
    private String avatar;
    private String description;
    private String personalLink;

    private Date createTime;
    private Date updateTime;

    public interface Update {
    }
}
