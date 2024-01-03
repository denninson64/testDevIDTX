package com.api.testdevidtx.repository;

import com.api.testdevidtx.entity.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long> {

    List<Photo> findAllByAlbumIdOrderByTitleAsc(Long id);
}
