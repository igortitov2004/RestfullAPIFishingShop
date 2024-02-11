package com.example.fishingshop.repositories;

import com.example.fishingshop.models.TypeOfReel;
import com.example.fishingshop.models.TypeOfRod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfReelRepository extends JpaRepository<TypeOfReel,Long> {

    List<TypeOfReel> findTypeOfReelByTypeContaining(String type);
    boolean existsTypeOfReelById(Long id);
    boolean existsTypeOfReelByType(String type);

    TypeOfReel findTypeOfReelByType(String type);
}
