package com.elevenst.terroir.product.registration.editor.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.After;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;



@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class EditorControllerTest {

    @Autowired
    private EditorController editorController;

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    private boolean refreshApplicationContext = true;

    private MediaType contentType = MediaType.APPLICATION_JSON_UTF8;

    private MediaType accept = MediaType.APPLICATION_JSON_UTF8;


    @Before
    public void before() throws Exception {
        if(mockMvc == null || refreshApplicationContext)
            this.mockMvc = webAppContextSetup(applicationContext).build();
    }

    @Test
    public void t010_getEditor() throws Exception {
        Long id = 1234L;
        String expectResponseJson = "{\"id\":1234, \"name\":\"cwoongc\"}";

        mockMvc.perform(
                get("/editor/" + id)
                .contentType(contentType)
                .accept(accept)
        ).andExpect(status().isOk()).andDo(print())
         .andExpect(content().json(expectResponseJson)).andDo(print())
         .andExpect(content().contentType(contentType)).andDo(print());
    }

    @Test
    public void t020_registerEditor() throws Exception{

        String contentJson = "{\"id\":1234, \"name\":\"cwoongc\", \"editorExampleCd\":\"01\"}";

        this.mockMvc.perform(
                post("/editor")
                .contentType(contentType)
                .accept(accept)
                .content(contentJson)
        ).andExpect(status().isOk()).andDo(print())
         .andExpect(content().contentType(contentType));
    }

    @Test
    public void t030_updateEditor() throws  Exception {

        String contentJson = "{\"id\":1234, \"name\":\"wcchoi\", \"editorExampleCd\":\"02\"}";

        this.mockMvc.perform(
                put("/editor")
                .contentType(contentType)
                .accept(accept)
                .content(contentJson)
        ).andExpect(status().isOk()).andDo(print())
         .andExpect(content().contentType(contentType));

    }

    @Test
    public void t040_deleteEditor() throws  Exception {

        Long id = 1234L;

        this.mockMvc.perform(
                delete("/editor/" + id)
                .contentType(contentType)
                .accept(accept)
        ).andExpect(status().isOk()).andDo(print())
        .andExpect(content().contentType(contentType)).andDo(print());
    }

    @After
    public void after() {

    }


}
