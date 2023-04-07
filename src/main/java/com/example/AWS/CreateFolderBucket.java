package com.example.AWS;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class CreateFolderBucket {
    public static void main(String[] args) {


        String bucketName = "hop-new-bucket";
        //The folderName must have / or not it is a object.
        String folderName = "my-photo/example";




        AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIA3AUPXG34B4YVL2UC", "eIceqlG2mrxadS0dapmm3+XeNnvnpcF4dcWkuy8X");
        S3Client client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.US_WEST_2)
                .build();

        //create folder in bucket
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(folderName)
                .build();

        client.putObject(request, RequestBody.empty());
    }
}
