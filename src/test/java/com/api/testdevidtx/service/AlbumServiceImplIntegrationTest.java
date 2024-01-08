package com.api.testdevidtx.service;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.repository.AlbumRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AlbumServiceImplIntegrationTest {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private AlbumRepository albumRepository;

    @AfterEach
    public void tearDown() {
        albumRepository.deleteAll();
    }

    @Test
    public void testInitDataSet() {
        List<Album> albums = createAlbums();

        List<Album> result = albumService.initDataSet(albums);

        assertEquals(albums.size(), result.size());
        assertEquals(albums, result);
    }

    @Test
    public void testGetAlbumsAll() {
        List<Album> albums = createAlbums();
        albumRepository.saveAll(albums);

        List<Album> result = albumService.getAlbumsAll();

        assertEquals(albums.size(), result.size());
    }

    @Test
    public void testGetAlbumById() {
        Album album = createAlbum(1L);
        albumRepository.save(album);

        Long albumId = album.getId();
        Album result = albumService.getAlbumById(albumId);

        assertNotNull(result);
        assertEquals(album, result);
    }

    @Test
    public void testSaveAlbum() {
        Album album = createAlbum(null);

        Album result = albumService.save(album);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(album.getTitle(), result.getTitle());
        assertEquals(album.getUserId(), result.getUserId());
    }

    @Test
    public void testDeleteAlbum() {
        Album album = createAlbum(null);
        albumRepository.save(album);

        Long albumId = album.getId();
        assertDoesNotThrow(() -> albumService.delete(albumId));

        Album result = albumService.getAlbumById(albumId);
        assertNull(result);
    }


    private List<Album> createAlbums() {
        List<Album> albums = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            albums.add(createAlbum(null));
        }

        return albums;
    }

    private Album createAlbum(Long albumId) {
        Album album = new Album();
        album.setTitle("Album Title " + albumId);
        album.setUserId(1L);

        if (albumId != null) {
            album.setId(albumId);
        }

        return album;
    }
}
