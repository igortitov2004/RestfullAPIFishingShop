package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.manufacturer.ManufacturerCreationRequest;
import com.example.fishingshop.DTOs.manufacturer.ManufacturerDTO;
import com.example.fishingshop.services.ManufacturerService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000"})
@SecurityRequirement(name = "bearerAuth")

@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;
    @GetMapping("/")
    public ResponseEntity<List<ManufacturerDTO>> list(@RequestParam(required = false) String name){
        return ResponseEntity.ok(manufacturerService.list(name));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<String> create(@Valid @RequestBody ManufacturerCreationRequest request){
        manufacturerService.add(request);
        return ResponseEntity.ok("Manufacturer " + request.getName() + " was created");
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        manufacturerService.delete(id);
        return ResponseEntity.ok("Manufacturer with id " + id + " was deleted");
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@Valid @RequestBody ManufacturerDTO manufacturerDTO){
        manufacturerService.edit(manufacturerDTO);
        return ResponseEntity.ok("Manufacturer with id " + manufacturerDTO.getId() + " was updated");
    }
}
