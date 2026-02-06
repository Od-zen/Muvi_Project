package jp.co.sss.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.ItemWithCategory;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.repository.ItemWithCategoryRepository;

/**
 * Контроллер для управления видео-товарами
 * Рефакторинг: использованы @GetMapping, выделена общая логика, улучшена читаемость
 */
@Controller
@RequestMapping("/videos")
public class ItemWithCategoryController {

    private final ItemWithCategoryRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final EntityManager entityManager;

    @Autowired
    public ItemWithCategoryController(
            ItemWithCategoryRepository itemRepository,
            CategoryRepository categoryRepository,
            EntityManager entityManager) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
    }

    /**
     * Отображение всех видео с категориями
     */
    @GetMapping
    public String showAllVideos(Model model) {
        model.addAttribute("items", itemRepository.findAll());
        return "items/item_category_list";
    }

    /**
     * Поиск по ID через кастомный Query
     */
    @GetMapping("/search/{id}")
    public String searchById(@PathVariable Integer id, Model model) {
        List<ItemWithCategory> items = itemRepository.findByIdQuery(id);
        model.addAttribute("items", items);
        return "items/item_category_list";
    }

    /**
     * Форма для JPQL поиска
     */
    @GetMapping("/jpql/form")
    public String showJpqlForm() {
        return "items/jpql_search_form";
    }

    /**
     * Поиск через Named Query (JPQL)
     */
    @GetMapping("/jpql/search")
    public String findByIdUsingJpql(@RequestParam Integer id, Model model) {
        TypedQuery<ItemWithCategory> query = entityManager.createNamedQuery(
                "ItemWithCategory.findById", ItemWithCategory.class);
        query.setParameter("id", id);

        model.addAttribute("item", query.getResultList());
        return "items/item_category_list";
    }

    // ========== Поиск по категориям ==========

    /**
     * Форма поиска по категории
     */
    @GetMapping("/search/category/form")
    public String showCategorySearchForm() {
        return "items/search_by_category_form";
    }

    /**
     * Поиск по ID категории (из формы)
     */
    @GetMapping("/search/category")
    public String searchByCategoryIdFromForm(@RequestParam int categoryId, Model model) {
        return searchByCategoryId(categoryId, model);
    }

    /**
     * Поиск по ID категории (из URL)
     */
    @GetMapping("/category/{categoryId}")
    public String searchByCategoryId(@PathVariable int categoryId, Model model) {
        Category category = createCategoryWithId(categoryId);
        List<ItemWithCategory> items = itemRepository.findByCategory(category);
        
        model.addAttribute("items", items);
        return "items/item_category_list";
    }

    /**
     * Форма поиска по имени категории
     */
    @GetMapping("/search/category-name/form")
    public String showCategoryNameSearchForm() {
        return "items/search_by_category_name_form";
    }

    /**
     * Поиск по имени категории
     */
    @GetMapping("/search/category-name")
    public String searchByCategoryName(@RequestParam String categoryName, Model model) {
        Category category = categoryRepository.findByName(categoryName);

        if (category == null) {
            model.addAttribute("items", null);
            model.addAttribute("message", "Категория \"" + categoryName + "\" не найдена");
            return "items/item_category_list";
        }

        List<ItemWithCategory> items = itemRepository.findByCategory(category);
        model.addAttribute("items", items);
        return "items/item_category_list";
    }

    // ========== Поиск по цене ==========

    /**
     * Форма поиска по цене
     */
    @GetMapping("/search/price/form")
    public String showPriceSearchForm() {
        return "items/search_by_price_form";
    }

    /**
     * Поиск видео дороже указанной цены
     */
    @GetMapping("/search/price/min")
    public String searchByMinPrice(@RequestParam Integer price, Model model) {
        List<ItemWithCategory> items = itemRepository.findByPriceGreaterThanEqual(price);
        model.addAttribute("items", items);
        return "items/item_category_list";
    }

    /**
     * Поиск видео дешевле указанной цены
     */
    @GetMapping("/search/price/max")
    public String searchByMaxPrice(@RequestParam Integer price, Model model) {
        List<ItemWithCategory> items = itemRepository.findByPriceLessThanEqual(price);
        model.addAttribute("items", items);
        return "items/item_category_list";
    }

    /**
     * Поиск в диапазоне цен
     */
    @GetMapping("/search/price/range")
    public String searchByPriceRange(
            @RequestParam Integer min,
            @RequestParam Integer max,
            Model model) {
        List<ItemWithCategory> items = itemRepository.findByPriceBetween(min, max);
        model.addAttribute("items", items);
        return "items/item_category_list";
    }

    // ========== Вспомогательные методы ==========

    /**
     * Создает объект Category с указанным ID
     */
    private Category createCategoryWithId(int categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return category;
    }
}