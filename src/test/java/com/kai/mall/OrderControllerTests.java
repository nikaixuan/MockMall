package com.kai.mall;

import com.kai.mall.service.OrderService;
import com.kai.mall.service.PropertyService;
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
public class OrderControllerTests {

    @Autowired
    OrderService orderService;
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
    public void testDeliverOrders() throws Exception{
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mvc.perform(MockMvcRequestBuilders.put("/deliveryOrder/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testListOrders() throws Exception{
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        this.mvc.perform(MockMvcRequestBuilders.get("/orders")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
