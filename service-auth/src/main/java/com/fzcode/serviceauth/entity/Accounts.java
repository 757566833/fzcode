package com.fzcode.serviceauth.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
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

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Accounts {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String aid;

    @Column(name = "create_time")
    @CreatedDate
    private Date createTime;

    @Column(name = "update_time")
    @LastModifiedDate
    private Date updateTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private Integer enabled = 1;

    @Column
    private Integer expired = 0;

    @Column
    private Integer locked = 0;

    @Column(name = "register_type")
    private Integer registerType;

    @Column(name = "delete_by")
    private String deleteBy;

    @Column(name = "is_delete")
    private Integer isDelete = 0;

    private Date deleteTime;
}
