package com.codegym.models.order;

import javax.persistence.*;

@Entity
@Table(name = "orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long productId;
    private Long quantity;
/*  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonBackReference
  private Order order;*/

    public OrderItem() {
    }

    public OrderItem(Long productId, Long quantity, Order order) {
        this.productId = productId;
        this.quantity = quantity;
        /*  this.order = order;*/
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

/*  public Order getOrder() {
    return order;
  }
  public void setOrder(Order order) {
    this.order = order;
  }*/

}
