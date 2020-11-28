package com.fzcode.noteservice.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;
@Data
@Entity
public class Texts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int nid;

    @Column(name = "create_time")
    @CreatedDate
    private  Date createTime;

    @Column(name = "update_time")
    @LastModifiedDate
    private Date updateTime;

    @Column(name = "create_by")
    @CreatedBy
    private String createBy;

    @Column(name = "update_by")
    @LastModifiedBy
    private String updateBy;

    private String title;

    private String description;

    @Column(name = "is_delete")
    private Boolean isDelete = false;


}
