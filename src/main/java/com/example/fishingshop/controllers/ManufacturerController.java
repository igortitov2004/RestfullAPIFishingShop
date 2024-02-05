package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.ManufacturerDTO;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerAlreadyExistException;
import com.example.fishingshop.exceptions.manufacturerExceptions.ManufacturerIsNotExistException;
import com.example.fishingshop.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @GetMapping("/")
    public ResponseEntity<List<ManufacturerDTO>> list(@RequestParam(required = false) String name){
        return ResponseEntity.ok(manufacturerService.list(name));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerDTO> manufacturerInfo(@PathVariable Long id){
        ManufacturerDTO manufacturerDTO = manufacturerService.getById(id);
        return ResponseEntity.ok(manufacturerDTO);
    }
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody ManufacturerDTO manufacturerDTO){
        manufacturerService.add(manufacturerDTO);
        return ResponseEntity.ok("Создан производитель" + manufacturerDTO.getName());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        manufacturerService.delete(id);
        return ResponseEntity.ok("Удален производитель c id " + id);
    }
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody ManufacturerDTO manufacturerDTO){
        manufacturerService.edit(manufacturerDTO);
        return ResponseEntity.ok("Обновлены данные производителя с id " + manufacturerDTO.getId());
    }
}
