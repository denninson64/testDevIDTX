package com.api.testdevidtx.repository;

import com.api.testdevidtx.entity.Album;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
public class AlbumRepositoryTest extends BaseRepositoryTest {

    private final String albumTitle = "myAlbum";

    private final String albumTitle2 = "myAlbum2";


    private final Long userId = 100L;

    @Test
    public void testAlbumCRUD() {
        Album createdAlbum = albumCreate();
        albumRead(createdAlbum);
        albumReadAll(1);
        albumUpdate(createdAlbum);

        createdAlbum = albumCreate();
        albumReadAll(2);
        albumRead(createdAlbum);
        albumDelete(createdAlbum);

    }

    private Album albumCreate() {
        Album album = new Album();
        album.setTitle(albumTitle);
        album.setUserId(userId);

        Album createdAlbum = albumRepository.save(album);
        assertEquals(albumTitle, createdAlbum.getTitle());
        assertEquals(userId, createdAlbum.getUserId());
        assertNotNull(createdAlbum.getId());

        return createdAlbum;
    }

    private void albumRead(Album createdAlbum) {
        Long createdAlbumId = createdAlbum.getId();
        Album album = albumRepository.findById(createdAlbumId).orElse(null);
        assertEquals(createdAlbum.getTitle(), album.getTitle());
        assertEquals(createdAlbum.getUserId(), album.getUserId());
        assertEquals(createdAlbumId, album.getId());
    }

    private void albumReadAll(int totalCnt) {
        List<Album> albums = (List<Album>) albumRepository.findAll();
        assertEquals(totalCnt, albums.size());
    }

    private void albumUpdate(Album createdAlbum) {
        Long createdAlbumId = createdAlbum.getId();
        Album album = albumRepository.findById(createdAlbumId).orElse(null);

        assertNotNull(album);

        album.setTitle(albumTitle2);
        Album updatedAlbum = albumRepository.save(album);

        assertEquals(updatedAlbum.getTitle(), albumTitle2);
        assertEquals(updatedAlbum.getUserId(), createdAlbum.getUserId());
        assertEquals(updatedAlbum.getId(), createdAlbumId);
    }

    private void albumDelete(Album createdAlbum) {
        albumRepository.delete(createdAlbum);

        Album album = albumRepository.findById(createdAlbum.getId()).orElse(null);
        assertNull(album, "Album should be null after deletion");
    }


}
