package com.api.testdevidtx.service;

import com.api.testdevidtx.entity.Photo;
import com.api.testdevidtx.exception.ObjectNotFoundException;
import com.api.testdevidtx.repository.PhotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;
    @Override
    public List<Photo> initDataSet(List<Photo> photos) {
        return (List<Photo>) photoRepository.saveAll(photos);
    }

    @Override
    public List<Photo> getPhotosAll() {
        return (List<Photo>) photoRepository.findAll();
    }

    @Override
    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Photo> getPhotosByAlbumId(Long albumId) {
        return photoRepository.findAllByAlbumIdOrderByTitleAsc(albumId);
    }

    @Override
    public Photo save(Photo photo){
        return photoRepository.save(photo);
    }

    @Transactional
    @Override
    public void delete(Long id){
        Photo photo = photoRepository.findById(id).orElse(null);
        if (photo != null) {
            photoRepository.delete(photo);
        } else {
            throw new ObjectNotFoundException("Photo not found for id :: " + id);
        }
    }
}
