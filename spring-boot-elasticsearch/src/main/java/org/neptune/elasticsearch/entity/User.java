package org.neptune.elasticsearch.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "user")
public class User {

    @Id
    private Long id;
    private String name;
    private Integer age;
}
