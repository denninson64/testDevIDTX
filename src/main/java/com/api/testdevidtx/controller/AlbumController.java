package com.api.testdevidtx.controller;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.exception.ObjectNotFoundException;
import com.api.testdevidtx.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(AlbumController.ALBUMS_BASE_URI)
public class AlbumController {

    public static final String ALBUMS_BASE_URI = "/api/albums";

    @Autowired
    private RestTemplate restTemplate;

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

    @GetMapping("/enrichedAlbums")
    public ResponseEntity<Object[]> getEnrichedAlbums() {
        String albumsEndpoint = "https://jsonplaceholder.typicode.com/albums";
        Object[] albums = restTemplate.getForObject(albumsEndpoint, Object[].class);

        String photosEndpoint = "https://jsonplaceholder.typicode.com/photos";
        Object[] photos = restTemplate.getForObject(photosEndpoint, Object[].class);

        enrichedAlbums(albums, photos);

        return new ResponseEntity<>(albums, HttpStatus.OK);
    }

    private void enrichedAlbums(Object[] albums, Object[] photos) {
        Map<Long, List<Object>> photosByAlbumId = Arrays.stream(photos)
                .collect(Collectors.groupingBy(photo -> Long.valueOf(String.valueOf(((Map) photo).get("albumId")))));

        for (Object album : albums) {
            Long albumId = Long.valueOf(String.valueOf(((Map) album).get("id")));
            ((Map) album).put("photos", photosByAlbumId.getOrDefault(albumId, Collections.emptyList()).toArray());
        }
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
