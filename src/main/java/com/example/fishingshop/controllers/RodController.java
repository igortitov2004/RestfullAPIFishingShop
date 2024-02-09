package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.rod.RodCreationRequest;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rod.RodEditRequest;
import com.example.fishingshop.services.RodService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> create(@RequestBody RodCreationRequest request){
        rodService.add(request);
        return ResponseEntity.ok("Создано удилище" + request.getName());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        rodService.delete(id);
        return ResponseEntity.ok("Удалено удилище c id " + id);
    }
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody RodEditRequest request){
        rodService.edit(request);
        return ResponseEntity.ok("Обновлены данные удилища с id " + request.getId());
    }
}
