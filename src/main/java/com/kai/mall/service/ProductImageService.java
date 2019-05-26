package com.kai.mall.service;

import com.kai.mall.dao.ProductImageDAO;
import com.kai.mall.pojo.OrderItem;
import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.ProductImage;
import com.kai.mall.util.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nikaixuan on 3/5/19.
 */
@Service
@CacheConfig(cacheNames="productImages")
public class ProductImageService {


    public static final String type_single = "single";
    public static final String type_detail = "detail";

    @Autowired
    ProductImageDAO productImageDAO;
    @Autowired
    ProductService productService;

    @CacheEvict(allEntries=true)
    public void add(ProductImage bean){
        productImageDAO.save(bean);
    }

    @CacheEvict(allEntries=true)
    public void delete(int id){
        productImageDAO.deleteById(id);
    }

    @Cacheable(key="'productImages-one-'+ #p0")
    public ProductImage get(int id){
        return productImageDAO.findById(id).get();
    }

    @Cacheable(key="'productImages-single-pid-'+ #p0.id")
    public List<ProductImage> listSingleProductImages(Product product) {
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product, type_single);
    }
    @Cacheable(key="'productImages-detail-pid-'+ #p0.id")
    public List<ProductImage> listDetailProductImages(Product product) {
        return productImageDAO.findByProductAndTypeOrderByIdDesc(product, type_detail);
    }

    public void setFirstProductImage(Product product) {
        ProductImageService productImageService = SpringContextUtil.getBean(ProductImageService.class);
        List<ProductImage> singleImages = productImageService.listSingleProductImages(product);
        if(!singleImages.isEmpty())
            product.setFirstProductImage(singleImages.get(0));
        else
            product.setFirstProductImage(new ProductImage());

    }
    public void setFirstProductImages(List<Product> products) {
        for (Product product : products)
            setFirstProductImage(product);
    }

    public void setFirstProdutImagesOnOrderItems(List<OrderItem> ois){
        for (OrderItem oi:ois){
            setFirstProductImage(oi.getProduct());
        }
    }

}
