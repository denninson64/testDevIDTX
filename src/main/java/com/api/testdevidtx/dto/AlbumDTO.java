package com.api.testdevidtx.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDTO {

    private Long id;
    private String title;
    private Long userId;
}
