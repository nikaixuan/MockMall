package com.kai.mall.dao;

import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nikaixuan on 3/5/19.
 */
@Repository
public interface ProductImageDAO extends JpaRepository<ProductImage, Integer> {

    List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);
}
