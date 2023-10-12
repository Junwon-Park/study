package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany
    @JoinTable(name = "category_item", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items = new ArrayList<>();

}
