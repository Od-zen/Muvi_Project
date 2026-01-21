package jp.co.sss.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jp.co.sss.shop.entity.Category;
import jp.co.sss.shop.entity.ItemWithCategory;
import jp.co.sss.shop.repository.CategoryRepository;
import jp.co.sss.shop.repository.ItemWithCategoryRepository;

@Controller
public class ItemWithCategoryController {

    @Autowired
    ItemWithCategoryRepository repository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping("/items/searchWithQuery/{id}")
    public String searchWithQuery(
        @PathVariable Integer id,
        Model model) {

    model.addAttribute("items", repository.findByIdQuery(id));
    return "items/item_category_list";
    }

    @RequestMapping("/items/jpql/form")
    public String showJpqlForm() {
        return "items/jpql_search_form";
    }

    @RequestMapping("/items/jpql/findById")
    public String findByIdByJpql(
            @RequestParam Integer id,
            Model model) {
        
        Query query = entityManager.createNamedQuery("ItemWithCategory.findById");

        query.setParameter("id", id);

        model.addAttribute("item", query.getResultList());

        return "items/item_category_list";
    }

    @RequestMapping("/items/findItemAndCategory")
    public String showList(Model model) {
        model.addAttribute("items", repository.findAll());
        return "items/item_category_list";
    }

    @RequestMapping("/items/searchForm")
    public String showCategorySearchForm() {
        return "items/search_by_category_form";
    }

    @RequestMapping("/items/searchByCategoryIdForm")
    public String searchByCategoryIdForm(
           @RequestParam int categoryId,
           Model model) {

        Category category = new Category();
        category.setId(categoryId);

        model.addAttribute("items", repository.findByCategory(category));
        return "items/item_category_list";
    }

    @RequestMapping("/items/searchByCategoryId/{categoryId}")
    public String searchByCategoryId(
        @PathVariable int categoryId,
        Model model) {

        // ① Categoryオブジェクトの生成
        Category category = new Category();

        // ② 受け取ったcategoryIdをセット
        category.setId(categoryId);

        // ③ リポジトリで条件検索
        List<ItemWithCategory> items = repository.findByCategory(category);

        // ④ モデルに保存
        model.addAttribute("items", items);

        // ⑤ 一覧画面へ
        return "items/item_category_list";
    }

    @RequestMapping("/items/searchByCategoryName")
    public String searchByCategoryName(
           @RequestParam String categoryName,
           Model model) {

        // ① カテゴリ名からカテゴリIDを取得
        Category category = categoryRepository.findByName(categoryName);

        if (category == null) {
           // 見つからない場合
           model.addAttribute("items", null);
           model.addAttribute("message", "カテゴリが見つかりませんでした");
           return "items/item_category_list";
        }

        // ② そのカテゴリの商品を検索
        model.addAttribute("items", repository.findByCategory(category));

        // ③ 一覧画面へ
        return "items/item_category_list";
    }

    @RequestMapping("/items/searchByCategoryNameForm")
    public String showCategoryNameSearchForm() {
        return "items/search_by_category_name_form";
    }

    @RequestMapping("/items/searchByPriceForm")
    public String showPriceForm() {
        return "items/search_by_price_form";
    }

    @RequestMapping("/items/searchByPriceMin")
    public String searchByPriceMin(
           @RequestParam Integer price,
           Model model) {

        model.addAttribute("items", repository.findByPriceGreaterThanEqual(price));
        return "items/item_category_list";
    }

    @RequestMapping("/items/searchByPriceMax")
    public String searchByPriceMax(
           @RequestParam Integer price,
           Model model) {

        model.addAttribute("items", repository.findByPriceLessThanEqual(price));
        return "items/item_category_list";
    }

    @RequestMapping("/items/searchByPriceRange")
    public String searchByPriceRange(
           @RequestParam Integer min,
           @RequestParam Integer max,
           Model model) {

        model.addAttribute("items", repository.findByPriceBetween(min, max));
        return "items/item_category_list";
    }

}