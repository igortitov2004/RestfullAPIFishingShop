package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.ReelDTO;
import com.example.fishingshop.DTOs.RodDTO;
import com.example.fishingshop.services.ReelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reels")
public class ReelController {

    private final ReelService reelService;

    @GetMapping("/")
    public ResponseEntity<List<ReelDTO>> list(@RequestParam(required = false) String name){
        return ResponseEntity.ok(reelService.list(name));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReelDTO> rodInfo(@PathVariable Long id){
        ReelDTO reelDTO = reelService.getById(id);
        return ResponseEntity.ok(reelDTO);
    }
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody ReelDTO reelDTO){
        reelService.add(reelDTO);
        return ResponseEntity.ok("Создана катушка" + reelDTO.getName());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        reelService.delete(id);
        return ResponseEntity.ok("Удалена катушка c id " + id);
    }
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody ReelDTO reelDTO){
        reelService.edit(reelDTO);
        return ResponseEntity.ok("Обновлены данные катушки с id " + reelDTO.getId());
    }

}
