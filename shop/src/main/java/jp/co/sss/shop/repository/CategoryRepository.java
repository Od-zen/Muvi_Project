package jp.co.sss.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.sss.shop.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Поиск категории по имени
     */
    Category findByName(String name);
}