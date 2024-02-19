package com.example.fishingshop.repositories;

import com.example.fishingshop.models.Rod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RodRepository extends JpaRepository<Rod,Long> {
    List<Rod> findRodByNameContaining(String name);
    boolean existsRodById(Long id);
    boolean existsRodByNameAndLengthAndWeightAndTestLoadAndPriceAndTypeIdAndManufacturerId(
            String name,
            Integer length,
            Integer weight,
            Integer testLoad,
            Double price,
            Long idType,
            Long idManufacturer
    );

    Optional<Rod> findRodByNameAndLengthAndWeightAndTestLoadAndPriceAndTypeIdAndManufacturerId(
            String name,
            Integer length,
            Integer weight,
            Integer testLoad,
            Double price,
            Long idType,
            Long idManufacturer
    );
}
