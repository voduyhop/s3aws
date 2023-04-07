package com.example.AWS;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;

import java.util.List;

public class ListBucket {


    public static void main(String[] args) {
        AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIA3AUPXG34B4YVL2UC", "eIceqlG2mrxadS0dapmm3+XeNnvnpcF4dcWkuy8X");
        S3Client client = S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.US_EAST_2)
                .build();

        ListBucketsRequest request = ListBucketsRequest.builder().build();
        ListBucketsResponse response = client.listBuckets(request);



        List<Bucket> buckets = response.buckets();
        for (Bucket bucket: buckets){
            System.out.println("-----------------------");
            System.out.println(bucket.name() +"--"+bucket.creationDate());
        }
    }
}
