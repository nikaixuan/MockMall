package com.kai.mall.web;

import com.kai.mall.pojo.*;
import com.kai.mall.service.*;
import com.kai.mall.util.Result;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by nikaixuan on 8/5/19.
 */
@RestController
public class ForeRESTController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    OrderService orderService;

    @GetMapping("/forehome")
    public Object home(){
        List<Category> cs = categoryService.list();
        productService.fill(cs);
        productService.fillByRow(cs);
        categoryService.removeCategoryFromProduct(cs);
        return cs;
    }

    @PostMapping("/foreregister")
    public Object register(@RequestBody User user){
        String name = HtmlUtils.htmlEscape(user.getName());
        user.setName(name);
        if (userService.isExist(name)){
            String message = "Name already exist";
            return Result.fail(message);
        }
        userService.add(user);
        return Result.success();
    }

    @PostMapping("/forelogin")
    public Object login(@RequestBody User userParam, HttpSession session) {
        String name =  userParam.getName();
        name = HtmlUtils.htmlEscape(name);

        User user =userService.get(name,userParam.getPassword());
        if(null==user){
            String message ="Wrong information";
            return Result.fail(message);
        }
        else{
            session.setAttribute("user", user);
            return Result.success();
        }
    }

    @GetMapping("/foreproduct/{pid}")
    public Object product(@PathVariable("pid") int pid ) {
        Product product = productService.getById(pid);

        List<ProductImage> productSingleImages = productImageService.listSingleProductImages(product);
        List<ProductImage> productDetailImages = productImageService.listDetailProductImages(product);

        product.setProductSingleImages(productSingleImages);
        product.setProductDetailImages(productDetailImages);

        List<Review> reviews = reviewService.list(product);
        List<PropertyValue> propertyValues = propertyValueService.listByProduct(product);
        productService.setSaleAndReviewNumber(product);
        productImageService.setFirstProductImage(product);

        Map<String,Object> map= new HashMap<>();
        map.put("product",product);
        map.put("pvs",propertyValues);
        map.put("reviews",reviews);

        return map;
    }

    @GetMapping("/forecategory/{cid}")
    public Object category(@PathVariable("cid") int cid, String sort){
        Category category = categoryService.get(cid);

        productService.fill(category);
        productService.setSaleAndReviewNumber(category.getProducts());
        categoryService.removeCategoryFromProduct(category);

        if(null!=sort){
            switch(sort){
                case "review":
                    Collections.sort(category.getProducts(),
                            (o1,o2)->Integer.compare(o1.getReviewCount(),o2.getReviewCount()));
                    break;
                case "date" :
                    Collections.sort(category.getProducts(),
                            (o1,o2)->o2.getCreateDate().compareTo(o1.getCreateDate()));
                    break;

                case "saleCount" :
                    Collections.sort(category.getProducts(),
                            (o1,o2)->Integer.compare(o1.getSaleCount(),o2.getSaleCount()));
                    break;

                case "price":
                    Collections.sort(category.getProducts(),
                            (o1,o2)->Float.compare(o1.getPromotePrice(),o2.getPromotePrice()));
                    break;

                case "all":
                    Collections.sort(category.getProducts(),
                            (o1,o2)->o2.getReviewCount()*o2.getSaleCount()-o1.getReviewCount()*o1.getSaleCount());
                    break;
            }
        }
        return category;
    }

    @PostMapping("foresearch")
    public Object search(String keyword){
        if (keyword==null){
            keyword="";
        }
        List<Product> products = productService.search(keyword,0,10);
        productImageService.setFirstProductImages(products);
        productService.setSaleAndReviewNumber(products);
        return products;
    }

    @GetMapping("forebuyone")
    public Object buyOne(int pid, int num, HttpSession session){
        return buyAddCart(pid,num,session);
    }

    public int buyAddCart(int pid, int num, HttpSession session){
        Product product = productService.getById(pid);
        int oiid = 0;
        boolean found = false;
        User user = (User) session.getAttribute("user");
        List<OrderItem> orderItemList = orderItemService.listByUser(user);
        for (OrderItem oi : orderItemList) {
            if(oi.getProduct().getId()==product.getId()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                oiid = oi.getId();
                break;
            }
        }

        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUser(user);
            oi.setProduct(product);
            oi.setNumber(num);
            orderItemService.add(oi);
            oiid = oi.getId();
        }
        return oiid;
    }

    @GetMapping("forebuy")
    public Object buy(String[] oiid, HttpSession session){
        List<OrderItem> orderItemList = new ArrayList<>();
        float total = 0;
        for (String oid:oiid){
            int id = Integer.parseInt(oid);
            OrderItem oi = orderItemService.get(id);
            total+=oi.getProduct().getPromotePrice()*oi.getNumber();
            orderItemList.add(oi);
        }
        productImageService.setFirstProdutImagesOnOrderItems(orderItemList);
        session.setAttribute("ois", orderItemList);

        Map<String,Object> map = new HashMap<>();
        map.put("orderItems", orderItemList);
        map.put("total", total);
        return Result.success(map);
    }

    @GetMapping("foreaddCart")
    public Object addCart(int pid, int num, HttpSession session) {
        buyAddCart(pid,num,session);
        return Result.success();
    }

    @GetMapping("forecart")
    public Object cart(HttpSession session) {
        User user =(User)  session.getAttribute("user");
        List<OrderItem> ois = orderItemService.listByUser(user);
        productImageService.setFirstProdutImagesOnOrderItems(ois);
        return ois;
    }

    @GetMapping("forechangeOrderItem")
    public Object changeOrderItem(HttpSession session, int pid, int num) {
        User user =(User)session.getAttribute("user");
        if(null==user)
            return Result.fail("Required login");

        List<OrderItem> ois = orderItemService.listByUser(user);
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId()==pid){
                oi.setNumber(num);
                orderItemService.update(oi);
                break;
            }
        }
        return Result.success();
    }

    @GetMapping("foredeleteOrderItem")
    public Object deleteOrderItem(HttpSession session,int oiid){
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return Result.fail("Required login");
        orderItemService.delete(oiid);
        return Result.success();
    }

    @PostMapping("forecreateOrder")
    public Object createOrder(@RequestBody Order order,HttpSession session){
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return Result.fail("Required login");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
        order.setOrderCode(orderCode);
        order.setCreateDate(new Date());
        order.setUser(user);
        order.setStatus(OrderService.waitPay);
        List<OrderItem> ois= (List<OrderItem>)  session.getAttribute("ois");

        float total =orderService.add(order,ois);

        Map<String,Object> map = new HashMap<>();
        map.put("oid", order.getId());
        map.put("total", total);

        return Result.success(map);
    }

    @GetMapping("forepaid")
    public Object paid(int oid) {
        Order order = orderService.get(oid);
        order.setStatus(OrderService.waitDelivery);
        order.setPayDate(new Date());
        orderService.update(order);
        return order;
    }

    @GetMapping("forebought")
    public Object bought(HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null==user)
            return Result.fail("Required login");
        List<Order> os= orderService.listByUserWithoutDelete(user);
        orderItemService.fill(os);
        orderService.removeOrderFromOrderItem(os);
        return os;
    }

    @GetMapping("foreconfirmPay")
    public Object confirmPay(int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        orderService.cacl(o);
        orderService.removeOrderFromOrderItem(o);
        return o;
    }

    @GetMapping("foreorderConfirmed")
    public Object orderConfirmed(int oid) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.waitReview);
        o.setConfirmDate(new Date());
        orderService.update(o);
        return Result.success();
    }

    @PutMapping("foredeleteOrder")
    public Object deleteOrder(int oid){
        Order o = orderService.get(oid);
        o.setStatus(OrderService.delete);
        orderService.update(o);
        return Result.success();
    }

    @GetMapping("forereview")
    public Object review(int oid) {
        Order o = orderService.get(oid);
        orderItemService.fill(o);
        orderService.removeOrderFromOrderItem(o);
        Product p = o.getOrderItems().get(0).getProduct();
        List<Review> reviews = reviewService.list(p);
        productService.setSaleAndReviewNumber(p);
        Map<String,Object> map = new HashMap<>();
        map.put("p", p);
        map.put("o", o);
        map.put("reviews", reviews);

        return Result.success(map);
    }

    @PostMapping("foredoreview")
    public Object doreview(HttpSession session,int oid,int pid,String content) {
        Order o = orderService.get(oid);
        o.setStatus(OrderService.finish);
        orderService.update(o);

        Product p = productService.get(pid);
        content = HtmlUtils.htmlEscape(content);

        User user =(User)  session.getAttribute("user");
        Review review = new Review();
        review.setContent(content);
        review.setProduct(p);
        review.setCreateDate(new Date());
        review.setUser(user);
        reviewService.add(review);
        return Result.success();
    }

    @GetMapping("forecheckLogin")
    public Object checkLogin( HttpSession session) {
        User user =(User)  session.getAttribute("user");
        if(null!=user)
            return Result.success();
        return Result.fail("Required login");
    }




}
