package com.fzcode.servicenote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "blog-note")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    @Id
    private String id;

    /**
     * 标题
     */
    @Field(type = FieldType.Text,searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    private String title;


    /**
     * 内容
     */
    @Field(type = FieldType.Text,searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    private String text;

    /**
     * 记录时间
     */
    @Field(type = FieldType.Auto)
    private List<Integer> categories;
}
