package com.furguard.backend.config.firebase;

import com.furguard.backend.exception.InternalServerErrorException;
import com.furguard.backend.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FirebaseStorageService {

    ResponseEntity uploadImage(MultipartFile file) throws IOException, NotFoundException, InternalServerErrorException;

    List<String> getAllImageUrls() throws NotFoundException;
}
