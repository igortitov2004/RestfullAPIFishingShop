package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.reel.ReelCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.reel.ReelEditRequest;
import com.example.fishingshop.services.ReelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/reels")
public class ReelController {

    private final ReelService reelService;
    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summary for management get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token / No access",
                            responseCode = "403"
                    )
            }

    )

    @GetMapping("/")
    public ResponseEntity<List<ReelDTO>> list(@RequestParam(required = false) String name){
        return ResponseEntity.ok(reelService.list(name));
    }
    @PreAuthorize("hasAnyAuthority('guest:read','user:read','admin:read')")
    @GetMapping("/{id}")
    public ResponseEntity<ReelDTO> reelInfo(@PathVariable Long id){
        ReelDTO reelDTO = reelService.getById(id);
        return ResponseEntity.ok(reelDTO);
    }
    @PreAuthorize("hasAuthority('admin:create')")
    @PutMapping("/")
    public ResponseEntity<String> create(@RequestBody ReelCreationRequest request){
        reelService.add(request);
        return ResponseEntity.ok("Создана катушка " + request.getName());
    }
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        reelService.delete(id);
        return ResponseEntity.ok("Удалена катушка c id " + id);
    }
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@RequestBody ReelEditRequest request){
        reelService.edit(request);
        return ResponseEntity.ok("Обновлены данные катушки с id " + request.getId());
    }

}
