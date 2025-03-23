package com.shahrozz.demo.Service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import org.springframework.stereotype.Service;

@Service
public class AwsScannerService {
    private final S3Client s3Client = S3Client.create();

    public void checkS3Buckets() {
        ListBucketsResponse response = s3Client.listBuckets();
        for (Bucket bucket : response.buckets()) {
            System.out.println("Bucket: " + bucket.name());
        }
    }
}
