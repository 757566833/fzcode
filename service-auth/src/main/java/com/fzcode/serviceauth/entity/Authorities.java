package com.fzcode.serviceauth.entity;


import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Authorities {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String authId;

    @Column
    private String account;

    @Column
    private String authority;

    @Column
    private String aid;

    @Column
    private String uid;

}
