package com.elevenst.terroir.product.registration.editor.service;

//import com.elevenst.terroir.product.registration.editor.mapper.EditorServiceTestMapper;
//import com.elevenst.terroir.product.registration.editor.vo.EditorVO;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@ActiveProfiles("local")
@SpringBootTest
public class EditorServiceTest {

    @Autowired
    private EditorServiceImpl editorService;

//    @Autowired
//    private EditorServiceTestMapper editorServiceTestMapper;

    @Before
    public void before() {

    }

//    @Test
//    public void t010_getEditor() {
//
//        long id = 1234L;
//
//        EditorVO editorVO = editorService.getEditor(id);
//
//        assertNotNull(editorVO);
//        assertEquals("cwoongc", editorVO.getName());
//        assertEquals("01", editorVO.getEditorExampleCd());
//
//    }

    @Test
    public void t020_insertEditor() {

    }

    @Test
    public void t030_updateEditor() {

    }

    @Test
    public void t040_deleteEditor() {

    }

    @After
    public void after() {

    }


}
