package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.ItemWithCategory;

@Repository
public interface ItemWithCategoryRepository extends JpaRepository<ItemWithCategory, Integer> {

    /**
     * Поиск по ID с использованием кастомного запроса
     */
    @Query("SELECT i FROM ItemWithCategory i WHERE i.id = :id")
    List<ItemWithCategory> findByIdQuery(@Param("id") Integer id);

    /**
     * Поиск по категории
     */
    List<ItemWithCategory> findByCategory(Category category);

    /**
     * Поиск товаров с ценой больше или равной указанной
     */
    List<ItemWithCategory> findByPriceGreaterThanEqual(Integer price);

    /**
     * Поиск товаров с ценой меньше или равной указанной
     */
    List<ItemWithCategory> findByPriceLessThanEqual(Integer price);

    /**
     * Поиск товаров в диапазоне цен
     */
    List<ItemWithCategory> findByPriceBetween(Integer minPrice, Integer maxPrice);
}
