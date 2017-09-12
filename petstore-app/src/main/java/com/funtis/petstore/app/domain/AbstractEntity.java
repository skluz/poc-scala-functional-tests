package com.funtis.petstore.app.domain;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Sławomir Kluz on 12/09/2017.
 */
@Table
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
}
