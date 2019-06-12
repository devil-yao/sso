package com.yj.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Document(indexName = "es-entity")
public class EsEntity {

    @Id
    private Integer id;

    @Field(copyTo = "showName")
    private String name;

    @Field(copyTo = "showName")
    private String desc;

    private String showName;
}
