package com.example.fishingshop.repositories;

import com.example.fishingshop.models.TypeOfRod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeOfRodRepository extends JpaRepository<TypeOfRod,Long> {
    List<TypeOfRod> findTypeOfRodByTypeContaining(String type);
    boolean existsTypeOfRodById(Long id);
    boolean existsTypeOfRodByType(String type);

    TypeOfRod findTypeOfRodByType(String type);
}
