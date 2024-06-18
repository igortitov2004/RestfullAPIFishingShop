package com.example.fishingshop.repositories;

import com.example.fishingshop.models.ImageReelsLink;
import com.example.fishingshop.models.ImageRodsLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageReelLinkRepository extends JpaRepository<ImageReelsLink,Long> {
    ImageReelsLink findImageReelsLinkByReelId(Long id);
}
