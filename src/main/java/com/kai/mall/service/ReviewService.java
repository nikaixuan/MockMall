package com.kai.mall.service;

import com.kai.mall.dao.ReviewDAO;
import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.Review;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by nikaixuan on 9/5/19.
 */
public class ReviewService {
    @Autowired
    ReviewDAO reviewDAO;
    @Autowired
    ProductService productService;

    public void add(Review review) {
        reviewDAO.save(review);
    }

    public List<Review> list(Product product){
        List<Review> result =  reviewDAO.findByProductOrderByIdDesc(product);
        return result;
    }

    public int getCount(Product product) {
        return reviewDAO.countByProduct(product);
    }

}
