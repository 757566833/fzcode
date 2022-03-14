package com.fzcode.servicenote.entity;

import com.fzcode.internalcommon.ifs.entity.ITexts;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Texts implements ITexts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tid;

    @Column(name = "create_time")
    @CreatedDate
    private Date createTime;

    @Column(name = "update_time")
    @LastModifiedDate
    private Date updateTime;

    @Column(name = "create_by")
//    @CreatedBy
    private String createBy;

    @Column(name = "update_by")
//    @LastModifiedBy
    private String updateBy;

    private String title;

    private String description;

    @Column(name = "value", columnDefinition="varchar(2048) COMMENT 'value'")
    private String value;

    @Column(name = "content", columnDefinition="varchar(2048) COMMENT 'content'")
    private String content;

    @Column(name = "is_delete")
    private Integer isDelete = 0;

    @Column(name = "top")
    private Integer top = 0;

    @Column(name = "type")
    private Integer type;

}
