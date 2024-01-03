package com.api.testdevidtx.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration
@WebAppConfiguration
public class BaseControllerTest {
    protected static final Long albumId1 = 1L;
    protected static final String title1 ="title1";
    protected static final Long  userId1 = 2L;
    protected static final Long albumId2 = 2L;
    protected static final String title2 ="title2";
    protected static final Long  userId2 = 4L;

    protected static final Long photoId1 = 21L;
    protected static final String photoTitle1 = "photoTitle1";
    protected static final String photoUrl1 = "photoUrl1";
    protected static final String photoThumbnailUrl1 = "photoThumbnailUrl1";
    protected static final Long photoId2 = 22L;
    protected static final String photoTitle2 = "photoTitle2";
    protected static final String photoUrl2 = "photoUrl2";
    protected static final String photoThumbnailUrl2 = "photoThumbnailUrl2";
    protected static final Long photoId3 = 23L;
    protected static final String photoTitle3 = "photoTitle3";
    protected static final String photoUrl3 = "photoUrl3";
    protected static final String photoThumbnailUrl3 = "photoThumbnailUrl3";
    protected static final Long photoId4 = 24L;
    protected static final String photoTitle4 = "photoTitle4";
    protected static final String photoUrl4 = "photoUrl4";

    @Autowired
    protected WebApplicationContext ctx;

    protected MockMvc mockMvc;

    protected static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
