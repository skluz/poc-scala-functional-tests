package com.funtis.petstore.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Sławomir Kluz on 12/09/2017.
 */
@Entity
@Table(name = "categories")
public class Category extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    public Category() {

    }

    public Category(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
