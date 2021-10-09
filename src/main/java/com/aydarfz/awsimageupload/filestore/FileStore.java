package com.aydarfz.awsimageupload.filestore;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

/**
 * This class stores files on aws s3
 */
@Service
public class FileStore {
    private final AmazonS3 s3; // A variable to store files

    @Autowired
    public FileStore(AmazonS3 s3) {
        this.s3 = s3;
    }

    /**
     * This method saves files. As parameters, it takes path (bucket name + any subfolders),
     * file name, optional metadata to store files, and the actual input
     */
    public void save(String path, String fileName, Optional<Map<String, String>> optionalMetadata, InputStream inputStream){
        ObjectMetadata metadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if(!map.isEmpty()){
                map.forEach(metadata::addUserMetadata);
            }
        });

        try{
            s3.putObject(path, fileName, inputStream, metadata);
        }
        catch (AmazonServiceException ex){
            throw new IllegalStateException("Failed to store file to s3", ex);
        }
    }
}
