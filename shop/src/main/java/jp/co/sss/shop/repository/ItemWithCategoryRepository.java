package jp.co.sss.shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.ItemWithCategory;

public interface ItemWithCategoryRepository 
       extends JpaRepository<ItemWithCategory, Integer> {

        @Query("SELECT i FROM ItemWithCategory i WHERE i.id = :id")
        List<ItemWithCategory> findByIdQuery(@Param("id") Integer id);

        // 外部キーによる条件検索
        List<ItemWithCategory> findByCategory(Category category);

        // 価格が以上（price >= ?）
        List<ItemWithCategory> findByPriceGreaterThanEqual(Integer price);

        // 価格が以下（price <= ?）
        List<ItemWithCategory> findByPriceLessThanEqual(Integer price);

        // 価格が範囲内
        List<ItemWithCategory> findByPriceBetween(Integer min, Integer max);

}
