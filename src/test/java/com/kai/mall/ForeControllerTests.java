package com.kai.mall;

import com.kai.mall.pojo.User;
import com.kai.mall.service.ProductService;
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
 * Created by nikaixuan on 24/5/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ForeControllerTests {

    @Autowired
    ProductService productService;
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
        User user =new User();
        user.setName("test");
        user.setPassword("12345");
        user.setId(2);
        session.setAttribute("user",user);
    }


    @Test
    public void buyOneTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/forebuyone")
                .param("pid", "1")
                .param("num", "2")
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void homeTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/forehome")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void buyTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/forebuy")
                .param("oiid", "11")
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
