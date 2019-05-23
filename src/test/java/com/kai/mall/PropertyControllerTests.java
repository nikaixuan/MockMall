package com.kai.mall;

import com.kai.mall.pojo.User;
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
 * Created by nikaixuan on 23/5/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PropertyControllerTests {

    @Autowired
    PropertyService propertyService;
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
        session = new MockHttpSession();
        User user =new User();
        session.setAttribute("user",user);
    }

    @Test
    public void getProperties() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/properties/4")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteProperties() throws Exception{
        mvc.perform(MockMvcRequestBuilders.delete("/properties/4")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateProperties() throws Exception{
        String json="{\"id\":5,\"name\":\"size\",\"category\":{\"id\":11,\"name\":\"iPhone\",\"products\":null,\"productsByRow\":null}}";
        mvc.perform(MockMvcRequestBuilders.put("/properties")
                .content(json.getBytes())
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}
