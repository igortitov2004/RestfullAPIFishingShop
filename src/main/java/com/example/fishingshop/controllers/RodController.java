package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.RodDTO;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerAlreadyExistException;
import com.example.fishingshop.exceptions.rodExceptions.RodIsNotExistException;
import com.example.fishingshop.services.RodService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rods")
public class RodController{
    private final RodService rodService;

    @GetMapping("/")
    public ResponseEntity<List<RodDTO>> list(@RequestParam(required = false) String name){
        return ResponseEntity.ok(rodService.list(name));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RodDTO> rodInfo(@PathVariable Long id){
        RodDTO rodDTO = rodService.getById(id);
        return ResponseEntity.ok(rodDTO);
    }
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody RodDTO rodDTO){
        rodService.add(rodDTO);
        return ResponseEntity.ok("Создано удилище" + rodDTO.getName());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        rodService.delete(id);
        return ResponseEntity.ok("Удалено удилище c id " + id);
    }
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody RodDTO rodDTO){
        rodService.edit(rodDTO);
        return ResponseEntity.ok("Обновлены данные удилища с id " + rodDTO.getId());
    }
}
