package com.kai.mall;

import com.kai.mall.service.OrderService;
import com.kai.mall.service.PropertyValueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by nikaixuan on 1/6/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyValueControllerTests {
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){

        session = new MockHttpSession();
//        User user =new User();
//        session.setAttribute("user",user);
    }

    @Test
    public void testListPv() throws Exception{
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mvc.perform(MockMvcRequestBuilders.get("/products/3/propertyValues")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testUpdatePv() throws Exception{
        String json="{\"id\":8,\"product\":{\"id\":3,\"name\":\"Honda\",\"subTitle\":\"Civic\",\"originalPrice\":20000.0,\"promotePrice\":8000.0,\"stock\":10,\"category\":{\"id\":14,\"name\":\"Car\",\"products\":null,\"productsByRow\":null},\"createDate\":\"2019-05-03T02:27:01.000+0000\",\"firstProductImage\":null,\"productSingleImages\":null,\"productDetailImages\":null,\"reviewCount\":0,\"saleCount\":0},\"property\":{\"id\":10,\"name\":\"Width\",\"category\":{\"id\":14,\"name\":\"Car\",\"products\":null,\"productsByRow\":null}},\"value\":3000}";
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mvc.perform(MockMvcRequestBuilders.put("/propertyValues")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.getBytes())
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
