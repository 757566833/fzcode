package com.fzcode.servicenote.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Table(name = "tbl_cid_tid")
public class CidTid {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    private Integer tid;

    private Integer cid;
}
