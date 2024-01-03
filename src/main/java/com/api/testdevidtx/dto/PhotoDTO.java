package com.api.testdevidtx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhotoDTO {
    private Long id;
    private String title;
    private String url;
    private Long albumId;
    private String thumbnailUrl;
}
