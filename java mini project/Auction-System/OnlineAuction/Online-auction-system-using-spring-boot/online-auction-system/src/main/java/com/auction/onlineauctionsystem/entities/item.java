package com.auction.onlineauctionsystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "items")
public class item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name",nullable = false)
    private String name;

    @Column(name = "item_price",nullable = false)
    private Double price;

    @Column(name = "item_description",nullable = false)
    private String description;

    @Column(name = "item_max_price",nullable = false)
    private Double maxPrice;


    public item(){}

    public item(String name, Double price, String description, Double maxPrice) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.maxPrice = maxPrice;
    }

    public Long getId()
    {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    


}