package com.api.testdevidtx.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.api.testdevidtx.service.AlbumService;
import com.api.testdevidtx.service.PhotoService;


@RunWith(SpringJUnit4ClassRunner.class)
public class InitControllerTest extends BaseControllerTest {
    @Mock
    private AlbumService albumService;

    @Mock
    private PhotoService photoService;

    @InjectMocks
    private InitController initController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(initController).build();
    }

    @Test
    public void initTest() throws Exception {
        mockMvc.perform(get("/init"))
                .andExpect(status().isOk());
    }

}