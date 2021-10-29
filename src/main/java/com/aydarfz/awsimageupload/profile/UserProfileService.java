package com.aydarfz.awsimageupload.profile;

import com.aydarfz.awsimageupload.bucket.BucketName;
import com.aydarfz.awsimageupload.filestore.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserProfileService {
    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles(){
        return userProfileDataAccessService.getUserProfiles();
    }

    void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        // Check if image is not empty
        isFileEmpty(file);

        // Check if file is an image
        isImage(file);

        // Check if the user exists in the database
        UserProfile user = getUserProfileOrThrow(userProfileId);

        // Grab some metadata from file
        Map<String, String> metadata = extractMetaData(file);

        // Store the image in S3 and update database (userProfileImageLink) with s3 image link
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String fileName = String.format("%s-%s", file.getName(), UUID.randomUUID());
        try{
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Grab some metadata from file
    private Map<String, String> extractMetaData(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    // Check if the user exists in the database
    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        return userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile not found", userProfileId)));
    }

    // Check if image is not empty
    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()){
            throw new IllegalStateException("Cannot ipload empty file [" + file.getSize() + "]");
        }
    }

    // Check if file is an image
    private void isImage(MultipartFile file) {
        if(!Arrays.asList(ContentType.IMAGE_JPEG, ContentType.IMAGE_PNG, ContentType.IMAGE_GIF).contains(file.getContentType())){
            throw new IllegalStateException("File must be an image");
        }
    }
}
