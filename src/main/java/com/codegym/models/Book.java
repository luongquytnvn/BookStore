package com.codegym.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 50)
    private String name;

    @NotBlank
    @PositiveOrZero
    private long price;

    @NotBlank
    private String picture;

    @NotBlank
    @Size(min = 4)
    private String description;

    @NotBlank
    @PositiveOrZero
    private long amount;

    @ManyToOne
    @JoinColumn(name = "author")
    private Author author;

    @OneToMany(targetEntity = Comment.class, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @ManyToMany
    private Set<Language> languages;

    @ManyToOne
    @JoinColumn(name = "publishing")
    private Publishing publishing;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    public Book() {
    }

    public Book(Long id, String name, long price, String picture, String description, long amount, Author author, Set<Comment> comments, Set<Language> languages, Publishing publishing, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.amount = amount;
        this.author = author;
        this.comments = comments;
        this.languages = languages;
        this.publishing = publishing;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Publishing getPublishing() {
        return publishing;
    }

    public void setPublishing(Publishing publishing) {
        this.publishing = publishing;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}