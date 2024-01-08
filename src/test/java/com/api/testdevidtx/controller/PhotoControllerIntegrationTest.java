package com.api.testdevidtx.controller;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.entity.Photo;
import com.api.testdevidtx.repository.AlbumRepository;
import com.api.testdevidtx.repository.PhotoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PhotoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private AlbumRepository albumRepository;
    @Test
    public void testGetAllPhotos() throws Exception {
        mockMvc.perform(get(PhotoController.PHOTOS_BASE_URI)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetPhotoById() throws Exception {
        Long existingPhotoId = 1L;
        List<Album> albums = createAndSaveAlbums();
        List<Photo> photos = createAndSavePhotos(albums);

        mockMvc.perform(get(PhotoController.PHOTOS_BASE_URI + "/{id}", existingPhotoId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetPhotosByAlbumId() throws Exception {
        Long existingAlbumId = 1L;
        List<Album> albums = createAndSaveAlbums();
        List<Photo> photos = createAndSavePhotos(albums);
        mockMvc.perform(get(PhotoController.PHOTOS_BASE_URI + "/byAlbum/{id}", existingAlbumId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    private List<Album> createAndSaveAlbums() {
        return IntStream.rangeClosed(1, 5)
                .mapToObj(this::createAndSaveAlbum)
                .toList();
    }

    private Album createAndSaveAlbum(int albumId) {
        Album album = new Album();
        album.setTitle("Album Title " + albumId);
        album.setUserId(1L);
        return albumRepository.save(album);
    }

    private List<Photo> createAndSavePhotos(List<Album> albums) {
        return IntStream.rangeClosed(1, 5)
                .mapToObj(photoId -> createAndSavePhoto(photoId, albums.get(0)))
                .toList();
    }

    private Photo createAndSavePhoto(int photoId, Album album) {
        Photo photo = new Photo();
        photo.setTitle("Photo Title " + photoId);
        photo.setThumbnailUrl("Thumbnail URL " + photoId);
        photo.setAlbum(album);
        return photoRepository.save(photo);
    }

}