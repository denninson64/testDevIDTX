package com.api.testdevidtx.controller;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.entity.Photo;
import com.api.testdevidtx.service.AlbumService;
import com.api.testdevidtx.service.PhotoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringJUnit4ClassRunner.class)
public class PhotoControllerTest extends BaseControllerTest {

    @Mock
    PhotoService photoService;

    @Mock
    AlbumService albumService;

    @InjectMocks
    protected PhotoController photoController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(photoController)
                .build();
    }

    @Test
    public void getPhotoTest() throws Exception {
        Photo expectedPhoto = new Photo();
        expectedPhoto.setTitle(photoTitle1);
        expectedPhoto.setUrl(photoUrl1);
        expectedPhoto.setThumbnailUrl(photoThumbnailUrl1);
        expectedPhoto.setId(photoId1);

        when(photoService.getPhotoById(photoId1)).thenReturn(expectedPhoto);

        mockMvc.perform(get(PhotoController.PHOTOS_BASE_URI + "/{id}", photoId1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(photoId1.intValue()))
                .andExpect(jsonPath("$.title").value(photoTitle1))
                .andExpect(jsonPath("$.url").value(photoUrl1))
                .andExpect(jsonPath("$.thumbnailUrl").value(photoThumbnailUrl1));
    }

    @Test
    public void getAllPhotosTest() throws Exception {
        List<Photo> photos = Arrays.asList(
                new Photo(photoId1, photoTitle1, photoUrl1, photoThumbnailUrl1),
                new Photo(photoId2, photoTitle2, photoUrl2, photoThumbnailUrl2)
        );

        when(photoService.getPhotosAll()).thenReturn(photos);

        mockMvc.perform(get(PhotoController.PHOTOS_BASE_URI)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(photoId1.intValue()))
                .andExpect(jsonPath("$[0].title").value(photoTitle1))
                .andExpect(jsonPath("$[0].url").value(photoUrl1))
                .andExpect(jsonPath("$[0].thumbnailUrl").value(photoThumbnailUrl1))
                .andExpect(jsonPath("$[1].id").value(photoId2.intValue()))
                .andExpect(jsonPath("$[1].title").value(photoTitle2))
                .andExpect(jsonPath("$[1].url").value(photoUrl2))
                .andExpect(jsonPath("$[1].thumbnailUrl").value(photoThumbnailUrl2));
    }

    @Test
    public void getAlbumPhotosTest() throws Exception {
        List<Photo> photos = Arrays.asList(
                new Photo(photoId1, photoTitle1, photoUrl1, photoThumbnailUrl1),
                new Photo(photoId2, photoTitle2, photoUrl2, photoThumbnailUrl2),
                new Photo(photoId3, photoTitle3, photoUrl3, photoThumbnailUrl3)
        );

        Album album = new Album();
        album.setTitle(title1);
        album.setUserId(userId1);
        album.setId(albumId1);
        album.setPhotos(photos);

        when(photoService.getPhotosByAlbumId(albumId1)).thenReturn(photos);

        // Test case where album exists
        when(albumService.getAlbumById(albumId1)).thenReturn(album);
                mockMvc.perform(get(PhotoController.PHOTOS_BASE_URI + "/byAlbum/{id}", albumId1)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(photoId1.intValue()))
                .andExpect(jsonPath("$[0].title").value(photoTitle1))
                .andExpect(jsonPath("$[0].url").value(photoUrl1))
                .andExpect(jsonPath("$[0].thumbnailUrl").value(photoThumbnailUrl1))
                .andExpect(jsonPath("$[1].id").value(photoId2.intValue()))
                .andExpect(jsonPath("$[1].title").value(photoTitle2))
                .andExpect(jsonPath("$[1].url").value(photoUrl2))
                .andExpect(jsonPath("$[1].thumbnailUrl").value(photoThumbnailUrl2))
                .andExpect(jsonPath("$[2].id").value(photoId3.intValue()))
                .andExpect(jsonPath("$[2].title").value(photoTitle3))
                .andExpect(jsonPath("$[2].url").value(photoUrl3))
                .andExpect(jsonPath("$[2].thumbnailUrl").value(photoThumbnailUrl3));

        // Test case where album does not exist
        when(albumService.getAlbumById(albumId1)).thenReturn(null);
        mockMvc.perform(get(PhotoController.PHOTOS_BASE_URI + "/byAlbum/{id}", albumId1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
