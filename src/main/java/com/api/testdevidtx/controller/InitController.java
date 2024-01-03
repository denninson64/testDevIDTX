package com.api.testdevidtx.controller;

import com.api.testdevidtx.dto.AlbumDTO;
import com.api.testdevidtx.dto.PhotoDTO;
import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.entity.Photo;
import com.api.testdevidtx.service.AlbumService;
import com.api.testdevidtx.service.PhotoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(InitController.INIT_BASE_URI)
public class InitController {

    private static final Log LOG = LogFactory.getLog(InitController.class);

    @Autowired
    AlbumService albumService;

    @Autowired
    PhotoService photoService;
    public static final String INIT_BASE_URI = "/init";

    @GetMapping
    public void initData(){
        RestTemplate restTemplate = new RestTemplate();
        // Retrieve albums and photos in JSON format and convert to DTO arrays
        AlbumDTO[] albums = restTemplate.getForObject("https://jsonplaceholder.typicode.com/albums", AlbumDTO[].class);
        PhotoDTO[] photos = restTemplate.getForObject("https://jsonplaceholder.typicode.com/photos", PhotoDTO[].class);

        // Initialize the objects in the db
        initData(albums,photos);
    }

    private void initData(AlbumDTO[] albumsDTOs, PhotoDTO[] photoDTOs) {
        List<Album> albums = Arrays.stream(albumsDTOs)
                .map(albumDTO -> {
                    Album album = new Album();
                    album.setId(albumDTO.getId());
                    album.setTitle(albumDTO.getTitle());
                    album.setUserId(albumDTO.getUserId());
                    return album;
                })
                .collect(Collectors.toList());

        List<Album> initAlbums = albumService.initDataSet(albums);
        LOG.info(initAlbums.size()+" album records of ("+albums.size()+") initialized");

        Map<Long, Album> albumMap = initAlbums.stream()
                .collect(Collectors.toMap(Album::getId, a -> a));

        List<Photo> photos = Arrays.stream(photoDTOs)
                .map(photoDTO -> {
                    Photo photo = new Photo();
                    photo.setId(photoDTO.getId());
                    photo.setTitle(photoDTO.getTitle());
                    photo.setUrl(photoDTO.getUrl());
                    photo.setThumbnailUrl(photoDTO.getThumbnailUrl());
                    photo.setAlbum(albumMap.get(photoDTO.getAlbumId()));
                    return photo;
                })
                .collect(Collectors.toList());

        List<Photo> initPhotos = photoService.initDataSet(photos);
        LOG.info(initPhotos.size()+" photo records of ("+photos.size()+") initialized");
    }

    @RequestMapping("/")
    public String hello(){
        return "Photo Album Manager running";
    }

}
