package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodCreationRequest;
import com.example.fishingshop.DTOs.typeOfRod.TypeOfRodDTO;
import com.example.fishingshop.services.TypeOfRodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/typesOfRods")
public class TypeOfRodController {
    private final TypeOfRodService typeOfRodService;

    @GetMapping("/")
    public ResponseEntity<List<TypeOfRodDTO>> list(@RequestParam(required = false) String type){
        return ResponseEntity.ok(typeOfRodService.list(type));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfRodDTO> typeOfRodInfo(@PathVariable Long id){
        TypeOfRodDTO typeOfRodDTO = typeOfRodService.getById(id);
        return ResponseEntity.ok(typeOfRodDTO);
    }
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody TypeOfRodCreationRequest request){
        typeOfRodService.add(request);
        return ResponseEntity.ok("Создан тип удилища " + request.getType());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        typeOfRodService.delete(id);
        return ResponseEntity.ok("Удален тип удилища c id " + id);
    }
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody TypeOfRodDTO typeOfRodDTO){
        typeOfRodService.edit(typeOfRodDTO);
        return ResponseEntity.ok("Обновлены данные типа удилища с id " + typeOfRodDTO.getId());
    }
}
