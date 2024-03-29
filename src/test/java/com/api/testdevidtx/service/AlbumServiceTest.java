package com.api.testdevidtx.service;


import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.repository.AlbumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest extends BaseServiceTest {

    @InjectMocks
    private AlbumServiceImpl albumService;

    @Mock
    private AlbumRepository albumRepository;

    @Test
    public void testInitDataSet() {
        List<Album> albums = createAlbums();
        when(albumRepository.saveAll(albums)).thenReturn(albums);

        List<Album> result = albumService.initDataSet(albums);

        assertEquals(albums.size(), result.size());
        verify(albumRepository, times(1)).saveAll(albums);
    }

    @Test
    public void testGetAlbumsAll() {
        List<Album> albums = createAlbums();
        when(albumRepository.findAll()).thenReturn(albums);

        List<Album> result = albumService.getAlbumsAll();

        assertEquals(albums.size(), result.size());
        verify(albumRepository, times(1)).findAll();
    }

    @Test
    public void testGetAlbumById() {
        Long albumId = 1L;
        Album album = createAlbum(albumId);
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));

        Album result = albumService.getAlbumById(albumId);

        assertNotNull(result);
        assertEquals(album, result);
        verify(albumRepository, times(1)).findById(albumId);
    }

    @Test
    public void testSaveAlbum() {
        Album album = createAlbum(1L);
        when(albumRepository.save(album)).thenReturn(album);

        Album result = albumService.save(album);

        assertNotNull(result);
        assertEquals(album, result);
        verify(albumRepository, times(1)).save(album);
    }

    @Test
    public void testDeleteAlbum() {
        Long albumId = 1L;
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(createAlbum(albumId)));

        assertDoesNotThrow(() -> albumService.delete(albumId));
        verify(albumRepository, times(1)).delete(any());
    }

    private List<Album> createAlbums() {
        List<Album> albums = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            albums.add(createAlbum(i));
        }

        return albums;
    }

    private Album createAlbum(Long albumId) {
        Album album = new Album();
        album.setId(albumId);
        album.setTitle("Album Title " + albumId);
        album.setUserId(1L);

        return album;
    }
}
