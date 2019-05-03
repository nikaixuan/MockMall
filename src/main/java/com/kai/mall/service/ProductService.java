package com.kai.mall.service;

import com.kai.mall.dao.ProductDAO;
import com.kai.mall.pojo.Category;
import com.kai.mall.pojo.Product;
import com.kai.mall.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nikaixuan on 3/5/19.
 */
@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;
    @Autowired
    CategoryService categoryService;

    public void add(Product bean){
        productDAO.save(bean);
    }

    public void update(Product bean){
        productDAO.save(bean);
    }

    public void delete(int id){
        productDAO.deleteById(id);
    }

    public Product getById(int id){
        return productDAO.findById(id).get();
    }

    public Page4Navigator<Product> list(int cid,int start, int size, int navigatePage){

        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start,size,sort);
        Category category = categoryService.get(cid);
        return new Page4Navigator<>(productDAO.findByCategory(category,pageable),navigatePage);
    }

    public List<Product> list(){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        return productDAO.findAll(sort);
    }
}
