package com.example.fishingshop.repositories;


import com.example.fishingshop.models.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {
    List<Manufacturer> findManufacturerByNameContaining(String name);

    boolean existsManufacturersByName(String name);
    boolean existsManufacturersById(Long id);

    Manufacturer findManufacturerByName(String name);
}
