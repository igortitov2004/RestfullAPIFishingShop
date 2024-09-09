package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.services.TypeOfRodService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000" })
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/typesOfRods")

public class TypeOfRodController {
    private final TypeOfRodService typeOfRodService;


    @GetMapping("/")
    public ResponseEntity<List<TypeOfRodDTO>> list(@RequestParam(required = false) String type){
        return ResponseEntity.ok(typeOfRodService.list(type));
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<String> create(@Valid @RequestBody TypeOfRodCreationRequest request){
        typeOfRodService.add(request);
        return ResponseEntity.ok("Type with name " + request.getType() + " was created");
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        typeOfRodService.delete(id);
        return ResponseEntity.ok("Type of rod with id " + id + " was deleted");
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@Valid @RequestBody TypeOfRodDTO typeOfRodDTO){
        typeOfRodService.edit(typeOfRodDTO);
        return ResponseEntity.ok("Type with id " + typeOfRodDTO.getId() + " was updated");
    }
}
