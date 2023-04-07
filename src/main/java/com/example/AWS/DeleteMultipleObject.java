package com.example.AWS;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.ArrayList;
import java.util.List;

public class DeleteMultipleObject {

    public static void main(String[] args) {

        String bucketName = "hop-new-bucket-5131";
        String key1 = "java logo.png";
        String key2 = "Screenshot (1).png";
        String key3 = "Screenshot_20221125_104646.png";



        AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIA3AUPXG34B4YVL2UC", "eIceqlG2mrxadS0dapmm3+XeNnvnpcF4dcWkuy8X");


        S3Client client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.US_WEST_2)
                .build();


        List<ObjectIdentifier> listObjects = new ArrayList<>();
        listObjects.add(ObjectIdentifier.builder().key(key1).build());
        listObjects.add(ObjectIdentifier.builder().key(key2).build());
        listObjects.add(ObjectIdentifier.builder().key(key3).build());


        DeleteObjectsRequest request = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(Delete.builder().objects(listObjects).build())
                .build();

        DeleteObjectsResponse deleteObjectsResponse = client.deleteObjects(request);
        System.out.println("Deleted: "+deleteObjectsResponse.hasDeleted());


    }
}
