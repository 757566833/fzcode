package com.fzcode.authservice.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aid;

    @Column(name = "create_time" )
    @CreatedDate
    private Date createTime;

    @Column(name = "update_time")
    @LastModifiedDate
    private Date updateTime;

    @Column(name="update_by")
    private String updateBy;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private Boolean enabled = true;

    @Column
    private Boolean expired = false;

    @Column
    private Boolean locked = false;

    @Column(name="register_type")
    private Integer registerType;

    @Column(name="delete_by")
    private String deleteBy;

    @Column(name = "is_delete")
    private Boolean isDelete = false;
}
