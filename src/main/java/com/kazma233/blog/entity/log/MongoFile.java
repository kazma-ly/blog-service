package com.kazma233.blog.entity.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @date 2017/10/18
 */
@Data
@Document(collection = "BlogData")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MongoFile {

    private String uid;

    private String name;

    private String contentType;
    private long size;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadDate;
    private String md5;

    private byte[] content;
}
