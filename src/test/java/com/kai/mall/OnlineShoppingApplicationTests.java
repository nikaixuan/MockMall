package com.kai.mall;

import com.kai.mall.pojo.Category;
import com.kai.mall.pojo.Product;
import com.kai.mall.pojo.Property;
import com.kai.mall.service.CategoryService;
import com.kai.mall.service.OrderItemService;
import com.kai.mall.service.ProductService;
import com.kai.mall.service.PropertyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineShoppingApplicationTests {

	@Autowired
	CategoryService categoryService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ProductService productService;


	@Test
	public void contextLoads() {
	}

	@Test
	public void testAddCategory(){
		for (int i=1;i<=2;i++){
			Category c = new Category();
			c.setId(i);
			c.setName("Test Category "+i);
			categoryService.add(c);
		}
	}

	@Test
	public void testUpdateCategory(){
		List<Category> categories = categoryService.list();
		Assert.assertEquals(4,categories.size());
	}

	@Test
	public void testDeleteCategory(){
		List<Category> categories = categoryService.list();
		categoryService.delete(1);
		categoryService.delete(2);
		Assert.assertEquals(2,categories.size());
	}

	@Test
	public void testAddProduct(){
		for (int i=1;i<=2;i++){
			Product p = new Product();
			p.setId(i);
			p.setName("Test Product "+i);
			productService.add(p);
		}
	}



}
