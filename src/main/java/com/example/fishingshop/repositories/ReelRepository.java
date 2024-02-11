package com.example.fishingshop.repositories;

import com.example.fishingshop.models.Reel;
import com.example.fishingshop.models.Rod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReelRepository extends JpaRepository<Reel,Long>{
    List<Reel> findReelByNameContaining(String name);
    boolean existsReelById(Long id);

    boolean existsReelByNameAndPriceAndTypeIdAndManufacturerId(String name,
                                                               Double price,
                                                               Long idType,
                                                               Long idManufacturer);
}
