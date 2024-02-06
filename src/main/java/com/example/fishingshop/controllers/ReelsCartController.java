package com.example.fishingshop.controllers;


import com.example.fishingshop.DTOs.reelsCart.ReelsCartDTO;
import com.example.fishingshop.DTOs.reelsCart.ReelsCartCreationRequest;
import com.example.fishingshop.services.ReelsCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reelsCarts")
public class ReelsCartController {
    private final ReelsCartService reelsCartService;

    @GetMapping("/user/{id}")
    public List<ReelsCartDTO> list(@PathVariable Long id){
        return reelsCartService.listByUserId(id);
    }

    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody ReelsCartCreationRequest reelsCartCreationRequest){

        reelsCartService.add(reelsCartCreationRequest);
        return ResponseEntity.ok("Товар добавлен в корзину");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        reelsCartService.deleteById(id);
        return ResponseEntity.ok("Катушка c id " + id+" удалена из корзины");
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteByUserId(@PathVariable Long id){
        reelsCartService.deleteByUserId(id);
        return ResponseEntity.ok("Катушки c id пользователя " + id + " удалены из корзины");
    }
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody ReelsCartDTO reelsCartDTO){
        reelsCartService.edit(reelsCartDTO);
        return ResponseEntity.ok("Обновлены данные катушки с id " + reelsCartDTO.getId());
    }
}
