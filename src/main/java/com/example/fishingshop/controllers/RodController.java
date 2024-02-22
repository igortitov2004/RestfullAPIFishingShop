package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.rod.RodCreationRequest;
import com.example.fishingshop.DTOs.rod.RodDTO;
import com.example.fishingshop.DTOs.rod.RodEditRequest;
import com.example.fishingshop.services.RodService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
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
    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping("/")
    public ResponseEntity<String> create(@RequestBody RodCreationRequest request){
        rodService.add(request);
        return ResponseEntity.ok("Rod " + request.getName() + " was created");
    }
    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        rodService.delete(id);
        return ResponseEntity.ok("Rod with id " + id + " was deleted");
    }
    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody RodEditRequest request){
        rodService.edit(request);
        return ResponseEntity.ok("Data of rod with id " + request.getId() + " was updated");
    }
}
