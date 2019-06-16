package com.yj.demo.entity;

import io.searchbox.annotations.JestId;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;

@Document(indexName = "es-entity",type = "entity")
public class EsEntity {

    @Id
    @JestId
    private Long id;

    @Field(copyTo = "showName")
    private String name;

    @Field(copyTo = "showName")
    private String desc;

    @InnerField(suffix = "show",type = FieldType.Keyword,fielddata = true)
    private String showName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}
