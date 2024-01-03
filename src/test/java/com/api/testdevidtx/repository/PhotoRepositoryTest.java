package com.api.testdevidtx.repository;

import com.api.testdevidtx.entity.Photo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
public class PhotoRepositoryTest extends BaseRepositoryTest {
    private final String photoTitle = "photoTitle";
    private final String photoTitle2 = "photoTitle2";
    private final String photoUrl = "photoUrl";
    private final String photoUrl2 = "photoUr2l";
    private final String photoThumbnailUrl = "photoThumbnailUrl";

    @Test
    public void testPhotoCRUD() {
        Photo createdPhoto = photoCreate();
        photoRead(createdPhoto);
        photoReadAll(1);
        photoUpdate(createdPhoto);
        createdPhoto = photoCreate();
        photoReadAll(2);
        photoDelete(createdPhoto);
        photoReadAll(1);

    }

    private Photo photoCreate() {
        Photo photo = new Photo();
        photo.setTitle(photoTitle);
        photo.setUrl(photoUrl);
        photo.setThumbnailUrl(photoThumbnailUrl);

        Photo createdPhoto = photoRepository.save(photo);
        assertEquals(photoTitle, createdPhoto.getTitle());
        assertEquals(photoUrl, createdPhoto.getUrl());
        assertEquals(photoThumbnailUrl, createdPhoto.getThumbnailUrl());
        assertNotNull(createdPhoto.getId());

        return createdPhoto;

    }

    private void photoRead(Photo createdPhoto) {
        Long createdPhotoId = createdPhoto.getId();
        Photo photo = photoRepository.findById(createdPhotoId).orElse(null);
        assertEquals(createdPhoto.getTitle(), photo.getTitle());
        assertEquals(createdPhoto.getUrl(), photo.getUrl());
        assertEquals(createdPhoto.getThumbnailUrl(), photo.getThumbnailUrl());
        assertEquals(createdPhotoId, photo.getId());

    }

    private void photoReadAll(int totalCnt) {
        List<Photo> photos = (List<Photo>) photoRepository.findAll();
        assertEquals(totalCnt, photos.size());

    }

    private void photoUpdate(Photo createdPhoto) {
        Long createdPhotoId = createdPhoto.getId();
        Photo photo = photoRepository.findById(createdPhotoId).orElse(null);

        assertNotNull(photo);

        photo.setTitle(photoTitle2);
        photo.setUrl(photoUrl2);
        Photo updatedPhoto = photoRepository.save(photo);

        assertEquals(updatedPhoto.getTitle(), photoTitle2);
        assertEquals(updatedPhoto.getUrl(), photoUrl2);
        assertEquals(updatedPhoto.getThumbnailUrl(), createdPhoto.getThumbnailUrl());
    }

    private void photoDelete(Photo createdPhoto) {
        photoRepository.delete(createdPhoto);

        Photo photo = photoRepository.findById(createdPhoto.getId()).orElse(null);
        assertNull(photo, "Photo should be null after deletion");
    }

}
