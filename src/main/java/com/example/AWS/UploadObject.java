package com.example.AWS;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.io.File;

public class UploadObject {

    public static void main(String[] args) {


        String bucketName = "hop-new-bucket-public-read";
        String fileName = "java logo.png";
        String filePath = "C:/Users/vdhop/Pictures/Screenshots/" + fileName;
        String folder = "image-new";
        String key = folder+"/"+fileName;



        AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIA3AUPXG34B4YVL2UC", "eIceqlG2mrxadS0dapmm3+XeNnvnpcF4dcWkuy8X");

        S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.US_WEST_2).build();

        //set object
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key) // if key consist folder, s3 will create a folder if the folder not exists
                .acl(ObjectCannedACL.PUBLIC_READ) // if we set this access we can read only through object link
                .build();

        s3Client.putObject(request, RequestBody.fromFile(new File(filePath)));

        System.out.println("Upload ok!!!");

        S3Waiter waiter = s3Client.waiter();
        HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        WaiterResponse<HeadObjectResponse> waiterResponse = waiter.waitUntilObjectExists(waitRequest);
        waiterResponse.matched().response().ifPresent(System.out::println);

        System.out.println("The file upload ok!");

    }
}
