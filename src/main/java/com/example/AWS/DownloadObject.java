package com.example.AWS;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.*;

public class DownloadObject {

    public static void main(String[] args) throws IOException {
        String bucketName = "hop-new-bucket-5131";
        String fileName = "java logo.png";
        String folder = "image-new";

        //if key consist folder name, we need to create folder or this folder is exists to download object
        String key = folder+"/"+fileName;
//        String key = fileName;



        AwsBasicCredentials credentials = AwsBasicCredentials.create("AKIA3AUPXG34B4YVL2UC", "eIceqlG2mrxadS0dapmm3+XeNnvnpcF4dcWkuy8X");
        S3Client s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.US_WEST_2).build();

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(request);

        //if we get only filename to generate/download file it doesn't need to create folder
        String fileNameSaved = new File(key).getName();
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileNameSaved));

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        while ( (bytesRead = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0, bytesRead);
        }
        inputStream.close();
        outputStream.close();
    }
}
