package com.funtis.petstore.app.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Created by Sławomir Kluz on 12/09/2017.
 */
@Table
@EntityListeners({AuditingEntityListener.class})
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    @CreatedDate
    private Instant createdDate;

    @LastModifiedDate
    private Instant lastModifiedDate;


    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
