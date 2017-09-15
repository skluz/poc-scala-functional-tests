package com.funtis.petstore.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Sławomir Kluz on 12/09/2017.
 */
@Entity
@Table(name = "pets")
public class Pet extends AbstractEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @OneToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "pets_categories_fk"))
    private Category category;

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(foreignKey = @ForeignKey(name = "pets_tags_pets_fk")),
            inverseForeignKey = @ForeignKey(name = "pets_tags_tags_fk")
    )
    private List<Tag> tags;

    @Column(nullable = false)
    @NotNull
    private PetStatus status;

    public Pet() {

    }

    public Pet(String name, Category category, PetStatus status, List<Tag> tags) {
        this.name = name;
        this.category = category;
        this.status = status;
        this.tags = tags;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public PetStatus getStatus() {
        return status;
    }

    public void setStatus(PetStatus status) {
        this.status = status;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

}
