package com.example.fishingshop.DTOs.reel.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageReelLinkDTO {
    private String link;
    private Long reelsId;
}
