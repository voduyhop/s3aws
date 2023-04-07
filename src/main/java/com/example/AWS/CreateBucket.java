package com.example.AWS;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.BucketCannedACL;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

public class CreateBucket {

    public static void main(String[] args) {

        String bucketName = "hop-new-bucket";


        //the name must be unique
        AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIA3AUPXG34B4YVL2UC", "eIceqlG2mrxadS0dapmm3+XeNnvnpcF4dcWkuy8X");
        S3Client client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.US_WEST_2)
                .build();
        //waiter to do some code when bucket created/exists
        S3Waiter waiter = client.waiter();

        CreateBucketRequest request = CreateBucketRequest.builder()
                .bucket(bucketName)
                .acl(BucketCannedACL.PUBLIC_READ) // set access
                .build();

        client.createBucket(request);
        System.out.println("Create bucket here !");
        HeadBucketRequest requestWait = HeadBucketRequest.builder().bucket(bucketName).build();
        //wait the headbucketrequest expression do desired state. Wait until the bucket is created and print out the response
        WaiterResponse<HeadBucketResponse> waiterResponse = waiter.waitUntilBucketExists(requestWait);
        waiterResponse.matched().response().ifPresent(System.out::println);
        System.out.println(bucketName +" is ready");

        System.out.println("---------------Create OK -------------------");
    }
}
