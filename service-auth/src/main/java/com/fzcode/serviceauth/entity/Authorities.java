package com.fzcode.serviceauth.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Authorities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authId;

    @Column
    private String account;

    @Column
    private String authority;

}
