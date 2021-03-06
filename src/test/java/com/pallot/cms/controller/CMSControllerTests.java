package com.pallot.cms.controller;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@TestPropertySource("classpath:application-test.properties")
@ContextConfiguration(classes={com.pallot.cms.CMSApplication.class})
@ActiveProfiles(profiles="integration")
public class CMSControllerTests {

    @Autowired
    private WebApplicationContext wac;
    
    @Autowired
    private Environment env;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void getProductBreifDescription() throws Exception {
        this.mockMvc.perform(get("/product/info/pen")
          .param("view", "writing-implement-brief"))
          .andExpect(status().isOk())
          .andExpect(content().string(containsString("Great Pen")));
    }
    
}