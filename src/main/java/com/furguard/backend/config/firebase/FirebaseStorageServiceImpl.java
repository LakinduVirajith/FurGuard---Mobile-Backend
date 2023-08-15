package com.furguard.backend.config.firebase;

import com.furguard.backend.common.CommonFunctions;
import com.furguard.backend.entity.PetProfile;
import com.furguard.backend.entity.ResponseMessage;
import com.furguard.backend.exception.InternalServerErrorException;
import com.furguard.backend.exception.NotFoundException;
import com.furguard.backend.repository.ProfileRepository;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirebaseStorageServiceImpl implements FirebaseStorageService{

    private final FirebaseApp firebaseApp;
    private final ProfileRepository profileRepository;
    private final CommonFunctions commonFunctions;

    @Value("${firebase.storage.folders.profile-images}")
    private String folderName;

    @Value("${firebase.storage.bucket}")
    private String storageBucket;

    @Override
    public ResponseEntity uploadImage(MultipartFile file) throws IOException, NotFoundException, InternalServerErrorException {
        Optional<PetProfile> profileOptional = profileRepository.findByUserUserId(commonFunctions.getUserId());
        if(profileOptional.isEmpty()){
            throw new NotFoundException("Pet profile not found");
        }

        PetProfile profile = profileOptional.get();
        if(profile.getImage() != null){
            this.deleteImage(profile);
        }

        String fileName = folderName + "/" + UUID.randomUUID() + "-" + profile.getPetId() + "-" + profile.getName();

        StorageClient storageClient = StorageClient.getInstance(firebaseApp);
        storageClient.bucket().create(fileName, file.getInputStream());
        String imageKey = storageClient.bucket().get(fileName).getMediaLink();

        if(imageKey != null){
            profile.setImage(fileName);
            profileRepository.save(profile);
        }else {
            throw new InternalServerErrorException("Oops! Something went wrong. Please try again later.");
        }

        ResponseMessage successResponse = new ResponseMessage();
        successResponse.setStatusCode(200);
        successResponse.setStatus(HttpStatus.OK);
        successResponse.setMessage("Profile image has been uploaded successfully");
        return ResponseEntity.ok().body(successResponse);
    }

    public void deleteImage(PetProfile profile) throws InternalServerErrorException {
        Bucket bucket = StorageClient.getInstance().bucket(storageBucket);
        boolean result =  bucket.get(profile.getImage()).delete();

        if(result){
            profile.setImage(null);
            profileRepository.save(profile);
        }else {
            throw new InternalServerErrorException("Oops! Something went wrong. Please try again later.");
        }
    }

    public List<String> getAllImageUrls() throws NotFoundException {
        List<String> imageUrls = new ArrayList<>();
        Storage storage = StorageOptions.getDefaultInstance().getService();

        String prefix = folderName + "/";
        Iterable<Blob> blobs = storage.list(storageBucket, Storage.BlobListOption.prefix(prefix)).iterateAll();

        for (Blob blob : blobs) {
            String imageUrl = blob.getName();
            imageUrls.add(imageUrl);
        }

        imageUrls.remove(0);
        if(imageUrls.isEmpty()) throw new NotFoundException("Sorry, no images could be found in the specified folder.");
        return imageUrls;
    }
}
