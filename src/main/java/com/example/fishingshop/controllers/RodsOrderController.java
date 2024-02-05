package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.RodsCartDTO;
import com.example.fishingshop.DTOs.RodsOrderDTO;
import com.example.fishingshop.exceptions.rodsCartExceptions.RodsCartIsNotExistsException;
import com.example.fishingshop.exceptions.rodsOrderExceptions.RodsOrderIsNotExistsException;
import com.example.fishingshop.services.RodsOrderService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rodsOrders")
public class RodsOrderController {
    private final RodsOrderService rodsOrderService;

    @GetMapping("/user/{id}")
    public List<RodsOrderDTO> list(@PathVariable Long id){
        return rodsOrderService.listByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RodsOrderDTO> rodsCartInfo(@PathVariable Long id){
        RodsOrderDTO rodsOrderDTO = rodsOrderService.getById(id);
        return ResponseEntity.ok(rodsOrderDTO);
    }
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody RodsOrderDTO rodsOrderDTO){
        rodsOrderService.add(rodsOrderDTO);
        return ResponseEntity.ok("Товар добавлен в корзину");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        rodsOrderService.deleteByUserId(id);
        return ResponseEntity.ok("Удалена корзина удилищ c id пользователя " + id);
    }
//    @PutMapping("/edit")
//    public ResponseEntity<String> update(@RequestBody RodsCartDTO rodsCartDTO){
//        rodsOrderService.edit(rodsCartDTO);
//        return ResponseEntity.ok("Обновлены данные удилища с id " + rodsCartDTO.getId());
//    }

}
