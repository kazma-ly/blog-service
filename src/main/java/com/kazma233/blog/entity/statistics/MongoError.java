package com.kazma233.blog.entity.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document("error")
public class MongoError {

    @Id
    private String id;

    private Integer code;
    private String message;
    private String path;
    private String msg;

    private LocalDateTime createTime;

}
