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

    @Test
    public void boughtTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/forebought")
                .contentType(MediaType.APPLICATION_JSON)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void createOrderTest() throws Exception{
        String json="{\"id\":6,\"user\":{\"id\":2,\"password\":\"12345\",\"name\":\"test\",\"salt\":null},\"orderCode\":\"201905211737007698221\",\"address\":\"415\\n604 Swanston Street\",\"post\":\"3053\",\"receiver\":\"a\",\"mobile\":\"401856369\",\"userMessage\":\"\",\"createDate\":\"2019-05-21T07:37:01.000+0000\",\"payDate\":\"2019-05-21T07:37:03.000+0000\",\"deliveryDate\":null,\"confirmDate\":\"2019-05-21T07:47:47.000+0000\",\"status\":\"finish\",\"orderItems\":[{\"id\":9,\"product\":{\"id\":2,\"name\":\"iPhoneXS MAX\",\"subTitle\":\"\",\"originalPrice\":0.0,\"promotePrice\":1500.0,\"stock\":100,\"category\":{\"id\":11,\"name\":\"iPhone\",\"products\":null,\"productsByRow\":null},\"createDate\":\"2019-05-03T02:18:24.000+0000\",\"firstProductImage\":{\"id\":19,\"type\":\"single\"},\"productSingleImages\":null,\"productDetailImages\":null,\"reviewCount\":0,\"saleCount\":0},\"user\":{\"id\":2,\"password\":\"12345\",\"name\":\"test\",\"salt\":null},\"order\":null,\"number\":1}],\"total\":1500.0,\"totalNumber\":1}";
        mvc.perform(MockMvcRequestBuilders.post("/forecreateOrder")
                .accept(MediaType.APPLICATION_JSON)
                .content(json.getBytes())
                .contentType(MediaType.APPLICATION_JSON)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void deleteOrderTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.put("/foredeleteOrder")
                .param("oid", "6")
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void doReviewTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.post("/foredoreview")
                .param("oid", "5")
                .param("pid", "8")
                .param("content", "good")
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void reviewTest() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/forereview")
                .param("oid", "5")
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
