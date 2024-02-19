package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerCreationRequest;
import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.services.ManufacturerService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    @GetMapping("/")
    public ResponseEntity<List<ManufacturerDTO>> list(@RequestParam(required = false) String name){
        return ResponseEntity.ok(manufacturerService.list(name));
    }
    @PreAuthorize("hasAuthority('admin:create')")
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody ManufacturerCreationRequest request){
        manufacturerService.add(request);
        return ResponseEntity.ok("Создан производитель " + request.getName());
    }
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        manufacturerService.delete(id);
        return ResponseEntity.ok("Удален производитель c id " + id);
    }
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody ManufacturerDTO manufacturerDTO){
        manufacturerService.edit(manufacturerDTO);
        return ResponseEntity.ok("Обновлены данные производителя с id " + manufacturerDTO.getId());
    }
}
