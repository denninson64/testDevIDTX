package com.api.testdevidtx.service;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.entity.Photo;

import com.api.testdevidtx.repository.PhotoRepository;
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
public class PhotoServiceTest extends BaseServiceTest {

    @InjectMocks
    private PhotoServiceImpl photoService;

    @Mock
    private PhotoRepository photoRepository;

    @Test
    public void testInitDataSet() {
        List<Photo> photos = createPhotos();
        when(photoRepository.saveAll(photos)).thenReturn(photos);

        List<Photo> result = photoService.initDataSet(photos);

        assertEquals(photos.size(), result.size());
        verify(photoRepository, times(1)).saveAll(photos);
    }

    @Test
    public void testGetPhotosAll() {
        List<Photo> photos = createPhotos();
        when(photoRepository.findAll()).thenReturn(photos);

        List<Photo> result = photoService.getPhotosAll();

        assertEquals(photos.size(), result.size());
        verify(photoRepository, times(1)).findAll();
    }

    @Test
    public void testGetPhotoById() {
        Long photoId = 1L;
        Photo photo = createPhoto(photoId);
        when(photoRepository.findById(photoId)).thenReturn(Optional.of(photo));

        Photo result = photoService.getPhotoById(photoId);

        assertNotNull(result);
        assertEquals(photo, result);
        verify(photoRepository, times(1)).findById(photoId);
    }

    @Test
    public void testGetPhotosByAlbumId() {
        Long albumId = 1L;
        List<Photo> photos = createPhotos();
        when(photoRepository.findAllByAlbumIdOrderByTitleAsc(albumId)).thenReturn(photos);

        List<Photo> result = photoService.getPhotosByAlbumId(albumId);

        assertEquals(photos.size(), result.size());
        verify(photoRepository, times(1)).findAllByAlbumIdOrderByTitleAsc(albumId);
    }

    @Test
    public void testSavePhoto() {
        Photo photo = createPhoto(1L);
        when(photoRepository.save(photo)).thenReturn(photo);

        Photo result = photoService.save(photo);

        assertNotNull(result);
        assertEquals(photo, result);
        verify(photoRepository, times(1)).save(photo);
    }

    @Test
    public void testDeletePhoto() {
        Long photoId = 1L;
        when(photoRepository.findById(photoId)).thenReturn(Optional.of(createPhoto(photoId)));

        assertDoesNotThrow(() -> photoService.delete(photoId));
        verify(photoRepository, times(1)).delete(any());
    }

    private List<Photo> createPhotos() {
        List<Photo> photos = new ArrayList<>();

        for (long i = 1; i <= 5; i++) {
            photos.add(createPhoto(i));
        }

        return photos;
    }

    private Photo createPhoto(Long photoId) {
        Photo photo = new Photo();
        photo.setId(photoId);
        String photoTitle = "photoTitle";
        photo.setTitle(photoTitle);
        String photoThumbnailUrl = "photoThumbnailUrl";
        photo.setThumbnailUrl(photoThumbnailUrl);

        Album album = new Album();
        album.setId(photoId);
        album.setTitle("Album Title " + photoId);
        album.setUserId(1L);
        photo.setAlbum(album);

        return photo;
    }
}
