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

import java.util.ArrayList;
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
    @Autowired
    ProductImageService productImageService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ReviewService reviewService;

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

    public void fill(Category category){
        List<Product> products = listByCategory(category);
        productImageService.setFirstProductImages(products);
        category.setProducts(productDAO.findByCategoryOrderById(category));

    }

    public void fill(List<Category> categories){
        if (categories!=null){
            for (Category category:categories){
                fill(category);
            }
        }
    }

    public void fillByRow(List<Category> categories){
        int row = 8;
        for (Category category:categories){
            List<List<Product>> productsByRow = new ArrayList<>();
            List<Product> products = listByCategory(category);
            for (int i=0;i<products.size();i+=row){
                int size = (i+row)>products.size()?products.size():(i+row);
                List<Product> productOfRow = products.subList(i,size);
                productsByRow.add(productOfRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    public List<Product> listByCategory(Category category){
        return productDAO.findByCategoryOrderById(category);
    }

    public void setSaleAndReviewNumber(Product p){
        int salesCount = orderItemService.getSaleCount(p);
        p.setSaleCount(salesCount);
        int reviewCount = reviewService.getCount(p);
        p.setReviewCount(reviewCount);
    }

    public void setSaleAndReviewNumber(List<Product> products){
        for (Product product:products){
            setSaleAndReviewNumber(product);
        }
    }

    public List<Product> search(String keyword, int start, int size){
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Pageable pageable = PageRequest.of(start,size,sort);
        List<Product> products = productDAO.findByNameLike("%"+keyword+"%",pageable);
        return products;
    }

    public Product get(int id){
        return productDAO.getOne(id);
    }
}
