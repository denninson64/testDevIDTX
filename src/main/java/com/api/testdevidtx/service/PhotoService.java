package com.api.testdevidtx.service;

import com.api.testdevidtx.entity.Photo;

import java.util.List;

public interface PhotoService {

    List<Photo> initDataSet(List<Photo> photos);
    List<Photo> getPhotosAll();
    Photo getPhotoById(Long id);
    List<Photo> getPhotosByAlbumId(Long id);
    Photo save(Photo photo);
    void delete(Long id);



}
