package com.kai.mall;

import com.kai.mall.pojo.Category;
import com.kai.mall.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineShoppingApplicationTests {

	@Autowired
	CategoryService categoryService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testAdd(){
		for (int i=1;i<=10;i++){
			Category c = new Category();
			c.setId(i);
			c.setName("Test Category "+i);
			categoryService.add(c);
		}
	}

}
