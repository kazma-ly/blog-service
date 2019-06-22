package com.kazma233.blog.entity.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author zly
 * @date 2017/10/18
 */
@Data
@Document(collection = "BlogData")
public class MongoFile {

    /**
     * 主键
     */
    @Id
    private String id;
    /**
     * 文件名称
     */
    private String name;
    /**
     * 文件类型
     */
    private String contentType;
    private long size;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadDate;
    private String md5;
    /**
     * 文件内容
     */
    private byte[] content;
    /**
     * 文件路径
     */
    private String path;

}
