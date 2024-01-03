package com.api.testdevidtx.service;

import com.api.testdevidtx.entity.Album;
import com.api.testdevidtx.repository.AlbumRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Override
    public List<Album> initDataSet(List<Album> albums) {
        return (List<Album>) albumRepository.saveAll(albums);
    }

    @Override
    public List<Album> getAlbumsAll() {
        return (List<Album>) albumRepository.findAll();
    }

    @Override
    public Album getAlbumById(Long id) {
        return albumRepository.findById(id).orElse(null);
    }

    @Override
    public Album save(Album album) {
        return albumRepository.save(album);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Album album = albumRepository.findById(id).orElse(null);
        if (album != null) {
            albumRepository.delete(album);
        } else {
            throw new RuntimeException("Album not found for id :: " + id);
        }
    }


}
