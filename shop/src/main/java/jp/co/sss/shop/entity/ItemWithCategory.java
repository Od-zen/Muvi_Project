package jp.co.sss.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

/**
 * Сущность видео-товара
 * Представляет фильм, сериал или другой видеоконтент
 */
@Entity
@Table(name = "items_with_categories")
@NamedQuery(
    name = "ItemWithCategory.findById",
    query = "SELECT i FROM ItemWithCategory i WHERE i.id = :id"
)
public class ItemWithCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private Category category;

    // Конструкторы
    public ItemWithCategory() {
    }

    public ItemWithCategory(String name, Integer price, Category category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    // Getters и Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "ItemWithCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + (category != null ? category.getName() : "null") +
                '}';
    }
}