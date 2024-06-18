package com.example.fishingshop.controllers;

import com.example.fishingshop.DTOs.reel.ReelCreationRequest;
import com.example.fishingshop.DTOs.reel.ReelDTO;
import com.example.fishingshop.DTOs.reel.ReelEditRequest;
import com.example.fishingshop.DTOs.reel.ReelsResponse;
import com.example.fishingshop.services.ReelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000" })
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/reels")

public class ReelController {

    private final ReelService reelService;
    @Operation(
            description = "Get endpoint for list of reels",
            summary = "List of reels",
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
    public ResponseEntity<List<ReelsResponse>> list(@RequestParam(required = false) String name){
        return ResponseEntity.ok(reelService.getList(name));
    }

    @Operation(
            description = "Get endpoint for information about one reel",
           // summary = "List of reels",
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
    @GetMapping("/{id}")
    public ResponseEntity<ReelDTO> reelInfo(@PathVariable Long id){
        ReelDTO reelDTO = reelService.getById(id);
        return ResponseEntity.ok(reelDTO);
    }
    @Operation(
            description = "Get endpoint for creating reel",
           // summary = "List of reels",
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
    @PreAuthorize("hasAuthority('admin:create')")
    @PostMapping("/")
    public ResponseEntity<String> create(@Valid @RequestBody ReelCreationRequest request){
        reelService.add(request);
        return ResponseEntity.ok("Reel " + request.getName() + " was created");
    }
    @Operation(
            description = "Get endpoint for deleting reel",
          //  summary = "List of reels",
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
    @PreAuthorize("hasAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        reelService.delete(id);
        return ResponseEntity.ok("Reel with id " + id + " was deleted");
    }
    @Operation(
            description = "Get endpoint for updating reel",
            //summary = "List of reels",
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
    @PreAuthorize("hasAuthority('admin:update')")
    @PutMapping("/edit")
    public ResponseEntity<String> update(@Valid @RequestBody ReelEditRequest request){
        reelService.edit(request);
        return ResponseEntity.ok("Data of reel with id " + request.getId() + " was updated");
    }

}
