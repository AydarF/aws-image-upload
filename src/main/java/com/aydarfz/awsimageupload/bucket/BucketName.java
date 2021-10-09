package com.aydarfz.awsimageupload.bucket;

public enum BucketName {
    PROFILE_IMAGE("test-aws-image-upload");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
