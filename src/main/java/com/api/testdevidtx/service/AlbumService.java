package com.api.testdevidtx.service;

import com.api.testdevidtx.entity.Album;

import java.util.List;

public interface AlbumService {

    List<Album> initDataSet(List<Album> albums);

    List<Album> getAlbumsAll();

    Album getAlbumById(Long id);

    Album save(Album album);

    void delete(Long id);


}
