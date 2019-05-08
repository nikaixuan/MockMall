package com.kai.mall.dao;

import com.kai.mall.pojo.Category;
import com.kai.mall.pojo.Product;
import com.kai.mall.util.Page4Navigator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nikaixuan on 3/5/19.
 */
@Repository
public interface ProductDAO extends JpaRepository<Product,Integer> {

    Page<Product> findByCategory(Category category, Pageable pageable);
    List<Product> findByCategoryOrderById(Category category);
}
