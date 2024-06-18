package com.example.fishingshop.repositories;

import com.example.fishingshop.models.ImageRodsLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRodsLinkRepository extends JpaRepository<ImageRodsLink,Long> {
    ImageRodsLink findImageRodsLinkByRodId(Long id);
}
