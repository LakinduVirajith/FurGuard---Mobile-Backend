package com.furguard.backend.controller;

import com.furguard.backend.dto.LostPetNoticeDTO;
import com.furguard.backend.entity.LostPetNotice;
import com.furguard.backend.exception.AlreadyExistException;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.service.LostPetNoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profile")
@RequiredArgsConstructor
@Tag(name = "Lost Pet Notice Controller")
public class LostPetNoticeController {

    private final LostPetNoticeService noticeService;

    @PostMapping("/notice")
    @Operation(
            summary = "Add Lost Pet Notice",
            description = "Add a new lost pet notice. Provide necessary details to create the notice."
    )
    public LostPetNoticeDTO addNotice(@Valid @RequestBody LostPetNotice lostPetNotice) throws NotFoundException, AlreadyExistException {
        return noticeService.addNotice(lostPetNotice);
    }

    @GetMapping("/notice")
    @Operation(
            summary = "Fetch Lost Pet Notice",
            description = "Fetch the lost pet notice associated with the pet profile."
    )
    public LostPetNoticeDTO fetchNotice() throws NotFoundException {
        return noticeService.fetchNotice();
    }

    @PutMapping("/notice")
    @Operation(
            summary = "Update Lost Pet Notice",
            description = "Update the existing lost pet notice. Provide updated details.")
    public LostPetNoticeDTO updateNotice(@Valid @RequestBody LostPetNotice lostPetNotice) throws NotFoundException {
        return noticeService.updateNotice(lostPetNotice);
    }

    @PutMapping("/notice/found")
    @Operation(
            summary = "Mark as Found",
            description = "Mark the lost pet notice as found and remove it within 7 days."
    )
    public ResponseEntity setAsFound() throws NotFoundException {
        return noticeService.setAsFound();
    }

    @PutMapping("/notice/not-found")
    @Operation(
            summary = "Mark as Not Found",
            description = "Mark the lost pet notice as not found."
    )
    public ResponseEntity setAsNotFound() throws NotFoundException {
        return noticeService.setAsNotFound();
    }

    @DeleteMapping("/notice")
    @Operation(
            summary = "Delete Lost Pet Notice",
            description = "Delete the existing lost pet notice."
    )
    public ResponseEntity deleteNotice() throws NotFoundException {
        return noticeService.deleteNotice();
    }
}
