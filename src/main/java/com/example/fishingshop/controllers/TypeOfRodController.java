package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.services.TypeOfRodService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/typesOfRods")
public class TypeOfRodController {
    private final TypeOfRodService typeOfRodService;
    @GetMapping("/")
    public ResponseEntity<List<TypeOfRodDTO>> list(@RequestParam(required = false) String type){
        return ResponseEntity.ok(typeOfRodService.list(type));
    }
    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody TypeOfRodCreationRequest request){
        typeOfRodService.add(request);
        return ResponseEntity.ok("Создан тип удилища " + request.getType());
    }
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        typeOfRodService.delete(id);
        return ResponseEntity.ok("Удален тип удилища c id " + id);
    }
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody TypeOfRodDTO typeOfRodDTO){
        typeOfRodService.edit(typeOfRodDTO);
        return ResponseEntity.ok("Обновлены данные типа удилища с id " + typeOfRodDTO.getId());
    }
}
