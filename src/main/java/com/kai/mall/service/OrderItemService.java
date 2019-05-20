package com.kai.mall.service;

import com.kai.mall.dao.OrderItemDAO;
import com.kai.mall.pojo.Order;
import com.kai.mall.pojo.OrderItem;
import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by nikaixuan on 7/5/19.
 */
@Service
public class OrderItemService {

    @Autowired
    OrderItemDAO orderItemDAO;
    @Autowired
    ProductImageService productImageService;

    public void fill(List<Order> orders) {
        for (Order order : orders)
            fill(order);
    }

    public void fill(Order order) {
        List<OrderItem> orderItems = listByOrder(order);
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi :orderItems) {
            total+=oi.getNumber()*oi.getProduct().getPromotePrice();
            totalNumber+=oi.getNumber();
            productImageService.setFirstProductImage(oi.getProduct());
        }
        order.setTotal(total);
        order.setOrderItems(orderItems);
        order.setTotalNumber(totalNumber);
    }

    public List<OrderItem> listByOrder(Order order) {
        return orderItemDAO.findByOrderOrderByIdDesc(order);
    }

    public int getSaleCount(Product product){
        List<OrderItem>list = orderItemDAO.findByProduct(product);
        int saleCount = 0;
        for (OrderItem oi:list){
            if (oi.getOrder()!=null&&oi.getOrder().getPayDate()!=null){
                saleCount+=oi.getNumber();
            }
        }
        return saleCount;
    }

    public List<OrderItem> listByProduct(Product product){
        return orderItemDAO.findByProduct(product);
    }

    public List<OrderItem> listByUser(User user) {
        return orderItemDAO.findByUserAndOrderIsNull(user);
    }

    public void update(OrderItem orderItem){
        orderItemDAO.save(orderItem);
    }


    public void add(OrderItem orderItem){
        orderItemDAO.save(orderItem);
    }

    public OrderItem get(int id){
        return orderItemDAO.getOne(id);
    }
}
