package com.furguard.backend.controller;

import com.furguard.backend.dto.SafeAreaDTO;
import com.furguard.backend.entity.SafeArea;
import com.furguard.backend.exception.AlreadyExistException;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.service.SafeAreaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
@RequiredArgsConstructor
@Tag(name = "Safe Area Controller")
public class SafeAreaController {

    private final SafeAreaService safeAreaService;

    @PostMapping("safe/area")
    @Operation(
            summary = "Set Safe Area",
            description = "Set a safe area for the target location."
    )
    public SafeAreaDTO setSafeArea(@Valid @RequestBody SafeArea safeArea) throws NotFoundException, AlreadyExistException {
        return safeAreaService.setSafeArea(safeArea);
    }

    @GetMapping("safe/area")
    @Operation(
            summary = "Get Safe Area",
            description = "Get the current safe area for the pet."
    )
    public SafeAreaDTO getSafeArea() throws NotFoundException {
        return safeAreaService.getSafeArea();
    }

    @Operation(
            summary = "Update Safe Area",
            description = "Update the safe area for the pet."
    )
    @PutMapping("safe/area")
    public SafeAreaDTO updateSafeArea(@Valid @RequestBody SafeArea safeArea) throws NotFoundException {
        return safeAreaService.updateSafeArea(safeArea);
    }

    @DeleteMapping("safe/area")
    @Operation(
            summary = "Remove Safe Area",
            description = "Remove the safe area that has been previously set."
    )
    public ResponseEntity removeSafeArea() throws NotFoundException {
        return safeAreaService.removeSafeArea();
    }
}
