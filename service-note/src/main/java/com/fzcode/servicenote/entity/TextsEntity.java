package com.fzcode.servicenote.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class TextsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tid;

    @Column(name = "create_time")
    @CreatedDate
    private Date createTime;

    @Column(name = "update_time")
    @LastModifiedDate
    private Date updateTime;

    @Column(name = "create_by")
//    @CreatedBy
    private Integer createBy;

    @Column(name = "update_by")
//    @LastModifiedBy
    private Integer updateBy;

    private String title;

    private String description;

    @Column(name = "raw",  columnDefinition="varchar(2048) COMMENT 'raw'")
    private String raw;

    @Column(name = "text", columnDefinition="varchar(2048) COMMENT 'text'")
    private String text;

    @Column(name = "html", nullable = false,columnDefinition="varchar(2048) COMMENT 'html'")
    private String html;

    @Column(name = "is_delete")
    private Integer isDelete = 0;

    @Column(name = "top")
    private Integer top = 0;

    @Column(name = "type")
    private Integer type;

}
