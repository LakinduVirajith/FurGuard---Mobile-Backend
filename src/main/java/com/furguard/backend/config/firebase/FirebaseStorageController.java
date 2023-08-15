package com.furguard.backend.config.firebase;

import com.furguard.backend.exception.InternalServerErrorException;
import com.furguard.backend.exception.NotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/storage")
@Tag(name = "Firebase Storage Controller")
public class FirebaseStorageController {

    private final FirebaseStorageService storageService;

    @PostMapping("/upload")
    @Operation(
            summary = "Upload Pet Profile Image",
            description = "Upload a profile image for your pet. Attach the image file using the 'file' parameter."
    )
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file) throws IOException, NotFoundException, InternalServerErrorException {
        return storageService.uploadImage(file);
    }

    @GetMapping("/view-all")
    @Operation(
            summary = "View All Pet Image URLs",
            description = "Retrieve a list of URLs for all the pet images in the specified folder."
    )
    public List<String> getAllImageUrls() throws NotFoundException {
        return storageService.getAllImageUrls();
    }
}
