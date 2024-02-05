package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.ManufacturerDTO;
import com.example.fishingshop.DTOs.TypeOfRodDTO;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerAlreadyExistException;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerIsNotExistException;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodAlreadyExistException;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodIsNotExistException;
import com.example.fishingshop.services.TypeOfRodService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> create(@RequestBody TypeOfRodDTO typeOfRodDTO){
        typeOfRodService.add(typeOfRodDTO);
        return ResponseEntity.ok("Создан тип удилища" + typeOfRodDTO.getType());
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
