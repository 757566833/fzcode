package com.fzcode.serviceauth.entity;


import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Users {

    @Id
    private int uid;


    @Column(name = "update_time")
    @LastModifiedDate
    private Date updateTime;

    @Column(name = "update_by")
    @LastModifiedBy
    private String updateBy;

    @Column
    private String username;

    @Column
    private String avatar;

    @Column(name = "id_card" )
    private String idCard;

    @Column(name = "github_url" )
    private String githubUrl;

    @Column
    private String blog;

}
