package com.fzcode.servicenote.entity;

import com.fzcode.internalcommon.ifs.entity.INote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Document(indexName = "blog-note")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note  implements INote {

    @Id
    private Integer id;

    /**
     * 标题
     */
    @Field(type = FieldType.Text,searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    private String title;


    /**
     * 内容
     */
    @Field(type = FieldType.Text,searchAnalyzer = "ik_smart",analyzer = "ik_max_word")
    private String content;

    /**
     * 分类
     */
    @Field(type = FieldType.Auto)
    private List<Integer> categories;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Auto)
    private Date createTime;

    /**
     * 修改时间
     */
    @Field(type = FieldType.Auto)
    private Date updateTime;

    /**
     * 创建人
     */
    @Field(type = FieldType.Auto)
    private Integer createBy;

    /**
     * 摘要（前n个字）
     */
    @Field(type = FieldType.Auto)
    private String summary;

}
