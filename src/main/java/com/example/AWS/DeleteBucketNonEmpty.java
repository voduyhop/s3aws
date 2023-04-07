package com.example.AWS;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

import java.util.ArrayList;
import java.util.List;

public class DeleteBucketNonEmpty {

    public static void main(String[] args) {

        //connect
        String bucketName = "hop-new-bucket";
        AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIA3AUPXG34B4YVL2UC", "eIceqlG2mrxadS0dapmm3+XeNnvnpcF4dcWkuy8X");
        S3Client client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.US_WEST_2)
                .build();


        //get list object
        ListObjectsRequest request = ListObjectsRequest.builder()
                .bucket(bucketName)
                .build();

        ListObjectsResponse listObjectsResponse = client.listObjects(request);
        List<S3Object> objectList = listObjectsResponse.contents();


        //map s3object to objectindentifier
        List<ObjectIdentifier> listObjectDelete = new ArrayList<>();

        for (S3Object object : objectList) {
            listObjectDelete.add(ObjectIdentifier.builder().key(object.key()).build());
        }


        //delete list object
        System.out.println("Here delete object");
        DeleteObjectsRequest requestDeleteObjects = DeleteObjectsRequest.builder()
                .bucket(bucketName)
                .delete(Delete.builder().objects(listObjectDelete).build())
                .build();
        client.deleteObjects(requestDeleteObjects);


        //delete bucket
        S3Waiter waiterBucket = client.waiter();
        HeadBucketRequest requestWaiterBucket = HeadBucketRequest.builder().bucket(bucketName).build();

        System.out.println("Here delete bucket");
        DeleteBucketRequest requestDeleteBucket = DeleteBucketRequest.builder()
                .bucket(bucketName)
                .build();

        client.deleteBucket(requestDeleteBucket);
        WaiterResponse<HeadBucketResponse> headBucketResponseWaiterResponse = waiterBucket.waitUntilBucketNotExists(requestWaiterBucket);
        headBucketResponseWaiterResponse.matched().response().ifPresent(x-> System.out.println("deleted bucket")); //not working



    }
}
