package com.api.testdevidtx.controller;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.entity.Photo;
import com.api.testdevidtx.exception.ObjectNotFoundException;
import com.api.testdevidtx.service.AlbumService;
import com.api.testdevidtx.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(PhotoController.PHOTOS_BASE_URI)
public class PhotoController {
    public static final String PHOTOS_BASE_URI = "/api/photos";

    @Autowired
    PhotoService photoService;

    @Autowired
    AlbumService albumService;

    @GetMapping
    public Iterable<Photo> getAllPhotos(){
        return photoService.getPhotosAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Photo getPhotoById(@PathVariable("id") Long id) {
        Photo existingPhoto = photoService.getPhotoById(id);
        if (existingPhoto != null) {
            return existingPhoto;
        } else {
            throw new ObjectNotFoundException(String.format("photo id %s was not found", id));
        }
    }

    @GetMapping("/byAlbum/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Iterable<Photo>> getPhotosByAlbumId(@PathVariable("id") Long id) {
        Album existingAlbum = albumService.getAlbumById(id);
        if (existingAlbum != null) {
            Iterable<Photo> photos = photoService.getPhotosByAlbumId(id);
            return ResponseEntity.ok(photos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
