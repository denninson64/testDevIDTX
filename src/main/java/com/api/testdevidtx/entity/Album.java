package com.api.testdevidtx.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long userId;

    @OneToMany(mappedBy = "album", fetch = FetchType.EAGER)
    private List<Photo> photos = new ArrayList<>();

    public Album(String title, Long userId){
        setTitle(title);
        setUserId(userId);
    }
}
