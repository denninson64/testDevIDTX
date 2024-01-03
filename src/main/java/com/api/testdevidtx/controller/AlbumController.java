package com.api.testdevidtx.controller;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.exception.ObjectNotFoundException;
import com.api.testdevidtx.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(AlbumController.ALBUMS_BASE_URI)
public class AlbumController {

    public static final String ALBUMS_BASE_URI = "/api/albums";

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public Iterable<Album> getAllAlbums() {
        return albumService.getAlbumsAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Album getAlbumById(@PathVariable("id") Long id) {
        Album existingAlbum = albumService.getAlbumById(id);
        if (existingAlbum != null) {
            return existingAlbum;
        } else {
            throw new ObjectNotFoundException(String.format("album id %s was not found", id));
        }
    }
}
