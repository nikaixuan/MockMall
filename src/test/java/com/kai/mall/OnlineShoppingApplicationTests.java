package com.kai.mall;

import com.kai.mall.pojo.*;
import com.kai.mall.service.*;
import com.kai.mall.util.Page4Navigator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineShoppingApplicationTests {

	@Autowired
	CategoryService categoryService;
	@Autowired
	OrderItemService orderItemService;
	@Autowired
	ProductService productService;
	@Autowired
	OrderService orderService;
	@Autowired
	PropertyService propertyService;
	@Autowired
	PropertyValueService propertyValueService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	UserService userService;



	@Test
	public void contextLoads() {
	}

	@Test
	public void testAddCategory(){
		for (int i=100;i<=101;i++){
			Category c = new Category();
			c.setName("Test Category "+i);
			categoryService.add(c);
		}
	}

	@Test
	public void testListCategory(){
		List<Category> categories = categoryService.list();
	}

	@Test
	public void testPageCategory(){
		Page4Navigator<Category> categories = categoryService.list(0,2,3);
	}

	@Test
	public void testUpdateCategory(){
		Category category = new Category();
		category.setId(18);
		category.setName("pc");
		categoryService.update(category);
	}

	@Test
	public void testAddProduct(){
		for (int i=1;i<=2;i++){
			Product p = new Product();
			p.setName("Test Product "+i);
			productService.add(p);
		}
	}

	@Test
	public void testDeleteCategory(){
		List<Category> categories = categoryService.list();
		categoryService.delete(29);
		categoryService.delete(30);
	}



	@Test
	public void testGetProduct(){
		Product product = productService.getById(1);
//		Assert.assertArrayEquals(product.getName().toCharArray(),"Test Product 1".toCharArray());
	}

	@Test
	public void testPageProduct(){
		Page4Navigator<Product> products = productService.list(14,0,2,3);
	}

	@Test
	public void testListProduct(){
		List<Product> products = productService.list();
	}

	@Test
	public void testUpdateProduct(){
		Product product = productService.getById(1);
		product.setName("Test product 1000");
		productService.update(product);
	}

	@Test
	public void testGetProperty(){
		propertyService.getById(6);
	}

	@Test
	public void testAddProperty(){
		Category category = categoryService.get(14);
		Property property = new Property();
		property.setId(10);
		property.setName("Width");
		property.setCategory(category);
		propertyService.add(property);
	}

	@Test
	public void testDeleteProperty(){
		propertyService.delete(10);
	}

	@Test
	public void testUpdateProperty(){
		Property property = propertyService.getById(5);
		property.setName("phone color");
		propertyService.update(property);
	}


	@Test
	public void testListPropertyByCategory(){
		Category category = categoryService.get(11);
		List<Property> properties = propertyService.listByCategory(category);
//		Assert.assertEquals(properties.size(),2);
	}


	@Test
	public void testGetPropertyValue(){
		Product product = productService.getById(8);
		List<PropertyValue> propertyValues = propertyValueService.listByProduct(product);
		Assert.assertEquals(propertyValues.size(),3);
	}

	@Test
	public void testGetByPropertyAndProduct(){
		Product product = productService.getById(8);
		Property property = propertyService.getById(5);
		PropertyValue propertyValue = propertyValueService.getByPropertyAndProduct(property,product);
		Assert.assertEquals(propertyValue.getValue(),"5000");
	}

	@Test
	public void testListPropertyValueByCategory(){
		Product product = productService.getById(3);
		propertyValueService.init(product);
	}

	@Test
	public void testUpdatePropertyValue(){
		Product product = productService.getById(3);
		Property property = propertyService.getById(8);
		PropertyValue propertyValue = propertyValueService.getByPropertyAndProduct(property,product);
		System.out.println(propertyValue);
		propertyValue.setValue("4000");
		propertyValueService.update(propertyValue);
	}

	@Test
	public void testGetAndUpdateOrder(){
		Order order = orderService.get(1);
		order.setAddress("abc carlton");
		orderService.update(order);
	}

//	@Test
//	public void testAddOrder(){
//		List<OrderItem> ois = new ArrayList<>();
//		OrderItem oi1 = orderItemService.get(11);
//		OrderItem oi2 = orderItemService.get(10);
//		ois.add(oi1);
//		ois.add(oi2);
//		Order order = orderService.get(5);
//		orderService.add(order,ois);
//
////		OrderItem mockOI = Mockito.mock(OrderItem.class);
////		when(mockOI.getProduct().getPromotePrice()).thenReturn(10.5f);
//
//	}

	@Test
	public void testListOrder(){
		orderService.list(1,2,2);
	}

	@Test
	public void testListOrderByUser(){
		User user = userService.findById(2);
		orderService.listByUserWithoutDelete(user);
	}

}
