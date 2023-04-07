package com.example.AWS;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;
import java.util.ListIterator;

public class ListObjectBucket {


    public static void main(String[] args) {

        String bucketName = "hop-public-images-bucket";

//        S3Client client = S3Client.builder().build(); // get information in system variable environment


        //get information in code
        AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIA3AUPXG34B4YVL2UC", "eIceqlG2mrxadS0dapmm3+XeNnvnpcF4dcWkuy8X");
        S3Client client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.US_EAST_2)
                .build();

        //get list object from bucket
        ListObjectsRequest request = ListObjectsRequest.builder()
                .bucket(bucketName)
                .prefix("action/") //list object in folder prefix name
                .delimiter("note") // exclude object have keyword, we use this.
                .maxKeys(100) //object result limit get
                .build();

        ListObjectsResponse response = client.listObjects(request);
        List<S3Object> objects = response.contents();

        ListIterator<S3Object> listIterator = objects.listIterator();

        System.out.println("---------------------------HERE-------------------");
        while (listIterator.hasNext()){
            S3Object s3Object = listIterator.next();
            System.out.println("-----------------------------------------------");
            System.out.println("Key: "+s3Object.key());
            System.out.println("Owner: "+s3Object.owner());
            System.out.println("Size: "+s3Object.size());
        }


    }
}
