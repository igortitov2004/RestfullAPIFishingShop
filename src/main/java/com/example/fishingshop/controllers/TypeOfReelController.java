package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelCreationRequest;
import com.example.fishingshop.DTOs.typeOfReel.TypeOfReelDTO;
import com.example.fishingshop.services.TypeOfReelService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/typeOfReels")
public class TypeOfReelController {
    private final TypeOfReelService typeOfReelService;
    @GetMapping("/")
    public ResponseEntity<List<TypeOfReelDTO>> list(@RequestParam(required = false) String type){
        return ResponseEntity.ok(typeOfReelService.list(type));
    }
    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping("/")
    public ResponseEntity<String> create(@Valid @RequestBody TypeOfReelCreationRequest request){
       typeOfReelService.add(request);
        return ResponseEntity.ok("Type with name " + request.getType() + " was created");
    }
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        typeOfReelService.delete(id);
        return ResponseEntity.ok("Type of reel with id " + id + " was deleted");
    }
    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@Valid @RequestBody TypeOfReelDTO typeOfReelDTO){
        typeOfReelService.edit(typeOfReelDTO);
        return ResponseEntity.ok("Type with id " + typeOfReelDTO.getId() + " was updated");
    }


}
