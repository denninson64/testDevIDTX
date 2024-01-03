package com.api.testdevidtx.service;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.entity.Photo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
public class PhotoServiceTest extends BaseServiceTest {

    private final String photoTitle = "photoTitle";
    private final String photoTitle2 = "photoTitle2";
    private final String photoUrl = "photoUrl";
    private final String photoUrl2 = "photoUr2l";
    private final String photoThumbnailUrl = "photoThumbnailUrl";
    private final String albumTitle = "myAlbum";
    private final String albumTitle2 = "myAlbum2";
    private final Long userId = 100L;

    @Autowired
    PhotoService photoService;

    @Autowired
    AlbumService albumService;

    @Test
    public void testPhotoCRUD() {
        Album album = new Album();
        album.setTitle(albumTitle);
        album.setUserId(userId);
        Album album1 = albumService.save(album);
        album = new Album();
        album.setTitle(albumTitle2);
        album.setUserId(userId);
        Album album2 = albumService.save(album);

        // Test crud methods
        Photo createdPhoto = photoCreate(album1);
        photoRead(createdPhoto);
        photoReadAll(1);
        photoUpdate(createdPhoto);
        createdPhoto = photoCreate(album1);
        photoReadAll(2);
        testAlbumPhotos(album1.getId(), 2);
        photoDelete(createdPhoto);
        photoReadAll(1);
        photoCreate(album2);
        photoCreate(album2);
        photoCreate(album2);
        photoCreate(album2);
        testAlbumPhotos(album1.getId(), 1);
        testAlbumPhotos(album2.getId(), 4);
    }

    private Photo photoCreate(Album album) {
        Photo photo = new Photo();
        photo.setTitle(photoTitle);
        photo.setUrl(photoUrl);
        photo.setThumbnailUrl(photoThumbnailUrl);
        photo.setAlbum(album);

        Photo createdPhoto = photoService.save(photo);
        assertEquals(photoTitle, createdPhoto.getTitle());
        assertEquals(photoUrl, createdPhoto.getUrl());
        assertEquals(photoThumbnailUrl, createdPhoto.getThumbnailUrl());
        assertNotNull(createdPhoto.getId());
        assertEquals(album.getId(), createdPhoto.getAlbum().getId());

        return createdPhoto;

    }

    private void photoRead(Photo createdPhoto) {
        Long createdPhotoId = createdPhoto.getId();
        Photo photo = photoService.getPhotoById(createdPhotoId);
        assertEquals(createdPhoto.getTitle(), photo.getTitle());
        assertEquals(createdPhoto.getUrl(), photo.getUrl());
        assertEquals(createdPhoto.getThumbnailUrl(), photo.getThumbnailUrl());
        assertEquals(createdPhotoId, photo.getId());
    }

    private void photoReadAll(int totalCnt) {
        List<Photo> photos = (List<Photo>) photoService.getPhotosAll();
        assertEquals(totalCnt, photos.size());
    }

    private void photoUpdate(Photo createdPhoto) {
        Long createdPhotoId = createdPhoto.getId();
        Photo photo = photoService.getPhotoById(createdPhotoId);

        assertNotNull(photo);

        photo.setTitle(photoTitle2);
        photo.setUrl(photoUrl2);
        Photo updatedPhoto = photoService.save(photo);

        assertEquals(updatedPhoto.getTitle(), photoTitle2);
        assertEquals(updatedPhoto.getUrl(), photoUrl2);
        assertEquals(updatedPhoto.getThumbnailUrl(), createdPhoto.getThumbnailUrl());
    }

    private void photoDelete(Photo createdPhoto) {
        photoService.delete(createdPhoto.getId());

        Photo photo = photoService.getPhotoById(createdPhoto.getId());
        assertNull(photo, "Photo should be null after deletion");
    }

    private void testAlbumPhotos(Long albumId, int photoCnt) {
        List<Photo> albumPhotos = photoService.getPhotosByAlbumId(albumId);
        assertEquals(photoCnt, albumPhotos.size());

    }
}
