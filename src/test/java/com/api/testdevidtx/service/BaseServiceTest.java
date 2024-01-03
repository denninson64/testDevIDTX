package com.api.testdevidtx.service;


import com.api.testdevidtx.repository.AlbumRepository;
import com.api.testdevidtx.repository.PhotoRepository;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BaseServiceTest {
    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Before
    public void setUp() throws Exception {
        photoRepository.deleteAll();
        albumRepository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        photoRepository.deleteAll();
        albumRepository.deleteAll();
    }
}
