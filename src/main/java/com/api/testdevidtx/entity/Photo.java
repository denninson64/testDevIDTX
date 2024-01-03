package com.api.testdevidtx.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    private String thumbnailUrl;

    @JsonIgnore
    @ManyToOne()
    private Album album;

    public Photo(Long id, String title, String url, String thumbNailUrl){
        setId(id);
        setTitle(title);
        setUrl(url);
        setThumbnailUrl(thumbNailUrl);
    }
}
