package com.api.testdevidtx.controller;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.entity.Photo;
import com.api.testdevidtx.service.AlbumService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AlbumControllerTest extends BaseControllerTest {

    @InjectMocks
    protected AlbumController albumController;
    @Mock
    AlbumService albumService;

    private static List<Album> getAlbums() {
        List<Album> albums = new ArrayList<>();
        List<Photo> photos = new ArrayList<>();
        Photo photo = new Photo();
        photo.setId(photoId1);
        photo.setUrl(photoUrl1);
        photo.setTitle(photoTitle1);
        photos.add(photo);
        photo = new Photo();
        photo.setId(photoId2);
        photo.setUrl(photoUrl2);
        photo.setTitle(photoTitle2);
        photos.add(photo);

        Album album = new Album();
        album.setTitle(title1);
        album.setUserId(userId1);
        album.setId(albumId1);
        albums.add(album);

        album = new Album();
        album.setTitle(title2);
        album.setUserId(userId2);
        album.setId(albumId2);
        photos = new ArrayList<Photo>();
        photo = new Photo();
        photo.setId(photoId3);
        photo.setUrl(photoUrl3);
        photo.setTitle(photoTitle3);
        photos.add(photo);
        albums.add(album);
        return albums;
    }

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(albumController)
                .build();
    }

    @Test
    public void getAlbumTest() throws Exception {
        Album expectedAlbum = new Album();
        expectedAlbum.setTitle(title1);
        expectedAlbum.setUserId(userId1);
        expectedAlbum.setId(albumId1);

        when(albumService.getAlbumById(albumId1)).thenReturn(expectedAlbum);

        mockMvc.perform(get("/api/albums/{id}", albumId1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(albumId1.intValue()))
                .andExpect(jsonPath("$.title").value(title1))
                .andExpect(jsonPath("$.userId").value(userId1.intValue()));

    }

    @Test
    public void getAlbumListTest() throws Exception {
        List<Album> albums = getAlbums();

        when(albumService.getAlbumsAll()).thenReturn(albums);

        mockMvc.perform(get("/api/albums")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", iterableWithSize(2)))
                .andExpect(jsonPath("$[0].title", is(title1)))
                .andExpect(jsonPath("$[0].userId", is(userId1.intValue())))
                .andExpect(jsonPath("$[0].id", is(albumId1.intValue())))
                .andExpect(jsonPath("$[1].title", is(title2)))
                .andExpect(jsonPath("$[1].userId", is(userId2.intValue())))
                .andExpect(jsonPath("$[1].id", is(albumId2.intValue())));

    }
}
