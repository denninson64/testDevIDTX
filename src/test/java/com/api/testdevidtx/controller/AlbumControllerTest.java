package com.api.testdevidtx.controller;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.entity.Photo;
import com.api.testdevidtx.service.AlbumService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.iterableWithSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class AlbumControllerTest extends BaseControllerTest {

    @Mock
    private RestTemplate restTemplate;

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
        photos = new ArrayList<>();
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
    public void testGetAlbumById() throws Exception {
        Album expectedAlbum = new Album();
        expectedAlbum.setTitle(title1);
        expectedAlbum.setUserId(userId1);
        expectedAlbum.setId(albumId1);

        when(albumService.getAlbumById(albumId1)).thenReturn(expectedAlbum);

        mockMvc.perform(get(AlbumController.ALBUMS_BASE_URI + "/{id}", albumId1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(albumId1.intValue()))
                .andExpect(jsonPath("$.title").value(title1))
                .andExpect(jsonPath("$.userId").value(userId1.intValue()));

    }

    @Test
    public void testGetAlbumList() throws Exception {
        List<Album> albums = getAlbums();

        when(albumService.getAlbumsAll()).thenReturn(albums);

        mockMvc.perform(get(AlbumController.ALBUMS_BASE_URI)
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

    @Test
    public void testGetAlbumByIdNotFound() throws Exception {
        when(albumService.getAlbumById(anyLong())).thenReturn(null);

        mockMvc.perform(get(AlbumController.ALBUMS_BASE_URI + "/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("album id 1 was not found")));
    }

    @Test
    public void testGetEnrichedAlbums() throws Exception {
        Object[] mockAlbums = {
                createAlbum(1L, "Album1", 1L),
                createAlbum(2L, "Album2", 2L)
        };
        Object[] mockPhotos = {
                createPhoto(1L, "Photo1", "https://photo1.jpg", 1L),
                createPhoto(photoId1, "Photo2", "https://photo2.jpg", 1L),
                createPhoto(3L, "Photo3", "https://photo3.jpg", 2L)
        };

        String albumsEndpoint = "https://jsonplaceholder.typicode.com/albums";
        when(restTemplate.getForObject(Mockito.eq(albumsEndpoint), Mockito.eq(Object[].class))).thenReturn(mockAlbums);
        String photosEndpoint = "https://jsonplaceholder.typicode.com/photos";
        when(restTemplate.getForObject(Mockito.eq(photosEndpoint), Mockito.eq(Object[].class))).thenReturn(mockPhotos);

        ResponseEntity<Object[]> responseEntity = albumController.getEnrichedAlbums();
        assertEquals(ResponseEntity.ok(mockAlbums), responseEntity);
    }

    private Map<String, Object> createAlbum(Long id, String title, Long userId) {
        Map<String, Object> album = new HashMap<>();
        album.put("id", id);
        album.put("title", title);
        album.put("userId", userId);
        return album;
    }

    private Map<String, Object> createPhoto(Long id, String title, String url, Long albumId) {
        Map<String, Object> photo = new HashMap<>();
        photo.put("id", id);
        photo.put("title", title);
        photo.put("url", url);
        photo.put("albumId", albumId);
        return photo;
    }

}
