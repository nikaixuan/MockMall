package com.kai.mall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Created by nikaixuan on 7/5/19.
 */
@Entity
@Table(name = "orderitem")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class OrderItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "pid")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
    @ManyToOne
    @JoinColumn(name = "oid")
    private Order order;

    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "Id=" + id +
                ", product=" + product +
                ", user=" + user +
                ", order=" + order +
                '}';
    }
}
