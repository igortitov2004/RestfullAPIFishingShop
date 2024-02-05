package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.RodsCartDTO;
import com.example.fishingshop.exceptions.rodExceptions.RodIsNotExistException;
import com.example.fishingshop.exceptions.rodsCartExceptions.RodsCartIsNotExistsException;
import com.example.fishingshop.services.RodsCartService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rodsCarts")
public class RodsCartController {
    private final RodsCartService rodsCartService;
    @GetMapping("/user/{id}")
    public List<RodsCartDTO> list(@PathVariable Long id){
       return rodsCartService.listByUserId(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RodsCartDTO> rodsCartInfo(@PathVariable Long id){
        RodsCartDTO rodsCartDTO = rodsCartService.getById(id);
        return ResponseEntity.ok(rodsCartDTO);
    }
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody RodsCartDTO rodsCartDTO){
        rodsCartService.add(rodsCartDTO);
        return ResponseEntity.ok("Товар добавлен в корзину");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        rodsCartService.delete(id);
        return ResponseEntity.ok("Удалена корзина удилищ c id " + id);
    }
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody RodsCartDTO rodsCartDTO){
        rodsCartService.edit(rodsCartDTO);
        return ResponseEntity.ok("Обновлены данные удилища с id " + rodsCartDTO.getId());
    }
}
