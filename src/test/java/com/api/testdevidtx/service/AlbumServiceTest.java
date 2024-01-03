package com.api.testdevidtx.service;

import com.api.testdevidtx.entity.Album;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AlbumServiceTest extends BaseServiceTest {

    private final String albumTitle = "myAlbum";
    private final String albumTitle2 = "myAlbum2";

    private final Long userId = 100L;

    @Autowired
    AlbumService albumService;

    @Test
    public void testAlbumServiceCRUD() {
        Album createdAlbum = albumCreate();
        albumRead(createdAlbum);
        albumReadAll(1);
        albumUpdate(createdAlbum);
        createdAlbum = albumCreate();
        albumReadAll(2);
        albumDelete(createdAlbum);
        albumReadAll(1);
    }

    private Album albumCreate() {
        Album album = new Album();
        album.setTitle(albumTitle);
        album.setUserId(userId);

        Album createdAlbum = albumService.save(album);
        assertEquals(albumTitle, createdAlbum.getTitle());
        assertEquals(userId, createdAlbum.getUserId());
        assertNotNull(createdAlbum.getId());

        return createdAlbum;
    }

    private void albumRead(Album createdAlbum) {
        Long createdAlbumId = createdAlbum.getId();
        Album album = albumService.getAlbumById(createdAlbumId);
        assertEquals(createdAlbum.getTitle(), album.getTitle());
        assertEquals(createdAlbum.getUserId(), album.getUserId());
        assertEquals(createdAlbumId, album.getId());
    }

    private void albumReadAll(int totalCnt) {
        List<Album> albums = albumService.getAlbumsAll();
        assertEquals(totalCnt, albums.size());
    }

    private void albumUpdate(Album createdAlbum) {
        Long createdAlbumId = createdAlbum.getId();
        Album album = albumService.getAlbumById(createdAlbumId);

        assertNotNull(album);

        album.setTitle(albumTitle2);
        Album updatedAlbum = albumService.save(album);

        assertEquals(updatedAlbum.getTitle(), albumTitle2);
        assertEquals(updatedAlbum.getUserId(), createdAlbum.getUserId());
        assertEquals(updatedAlbum.getId(), createdAlbumId);
    }

    private void albumDelete(Album createdAlbum) {
        albumService.delete(createdAlbum.getId());
        Album album = albumService.getAlbumById(createdAlbum.getId());
        assertNull(album);
    }
}
