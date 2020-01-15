package com.codegym.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4,max = 50)
    private String name;

    private Date date;

    @NotBlank
    @Size(min = 5)
    private String content;


    @ManyToOne
    private Book book;

    public Comment() {
    }

    public Comment(Long id, String name, Date date, String content, Book book) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.content = content;
        this.book = book;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Book getComment() {
        return book;
    }

    public void setComment(Book book) {
        this.book = book;
    }
}
