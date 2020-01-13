package com.codegym.models;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private long price;
    private String description;
    private long amount;
    private Date dateCreate;
    private long vote = 0;

    @OneToMany
    private List<BookPicture> bookPictures;

    @ManyToMany
    private List<Author> authors;

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public long getVote() {
        return vote;
    }

    public void setVote(long vote) {
        this.vote = vote;
    }

    public List<BookPicture> getBookPictures() {
        return bookPictures;
    }

    public void setBookPictures(List<BookPicture> bookPictures) {
        this.bookPictures = bookPictures;
    }


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

    public Book(String name, long price, String description, long amount, Date dateCreate, List<BookPicture> bookPictures, List<Author> authors, Set<Comment> comments, Set<Language> languages, Publishing publishing, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
        this.dateCreate = dateCreate;
        this.bookPictures = bookPictures;
        this.authors = authors;
        this.comments = comments;
        this.languages = languages;
        this.publishing = publishing;
        this.category = category;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
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