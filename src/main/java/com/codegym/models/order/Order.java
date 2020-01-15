package com.codegym.models.order;

import com.codegym.models.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ManyToAny;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //  private Long userId;
    @ManyToOne()
    /*@JsonBackReference*/
    private User user;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private Status status;

    /*
      @OneToMany(targetEntity = OrderItem.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    */
    @OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL)
    private List<OrderItem> orderItem;

    private Double total;

    public Order() {
    }

    public Order(User user, @NotBlank Date date, Status status, List<OrderItem> orderItem, Double total) {
        this.user = user;
        this.date = date;
        this.status = status;
        this.orderItem = orderItem;
        this.total = total;
    }

    public Order(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
