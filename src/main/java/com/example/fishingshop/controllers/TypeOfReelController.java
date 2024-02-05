package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.TypeOfReelDTO;
import com.example.fishingshop.DTOs.TypeOfRodDTO;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodAlreadyExistException;
import com.example.fishingshop.exceptions.typeOfRodExceptions.TypeOfRodIsNotExistException;
import com.example.fishingshop.services.TypeOfReelService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/typeOfReels")
public class TypeOfReelController {
    private final TypeOfReelService typeOfReelService;
    @GetMapping("/")
    public ResponseEntity<List<TypeOfReelDTO>> list(@RequestParam(required = false) String type){
        return ResponseEntity.ok(typeOfReelService.list(type));
    }
    @GetMapping("/{id}")
    public ResponseEntity<TypeOfReelDTO> typeOfReelInfo(@PathVariable Long id){
        TypeOfReelDTO typeOfReelDTO = typeOfReelService.getById(id);
        return ResponseEntity.ok(typeOfReelDTO);
    }
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody TypeOfReelDTO typeOfReelDTO){
       typeOfReelService.add(typeOfReelDTO);
        return ResponseEntity.ok("Создан тип катушки" + typeOfReelDTO.getType());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        typeOfReelService.delete(id);
        return ResponseEntity.ok("Удален тип катушки c id " + id);
    }
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody TypeOfReelDTO typeOfReelDTO){
        typeOfReelService.edit(typeOfReelDTO);
        return ResponseEntity.ok("Обновлены данные типа катушки с id " + typeOfReelDTO.getId());
    }


}
