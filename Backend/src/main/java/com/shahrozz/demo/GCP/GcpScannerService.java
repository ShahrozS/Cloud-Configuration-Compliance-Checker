package com.shahrozz.demo.GCP;

import com.google.api.services.storage.Storage;
import com.google.cloud.storage.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GcpScannerService {

    @Autowired
    private Storage storage;

    public List<Bucket> checkPublicBuckets() throws Exception {
        List<Bucket> publicBuckets = new ArrayList<>();
        for (Bucket bucket : storage.list().iterateAll()) {
            if(bucket.getIamConfiguration().getPublicAccessPrevention() == null ||
                    !bucket.getIamConfiguration().getPublicAccessPrevention().equals("enforced")) {
                publicBuckets.add(bucket);
            }
        }
        return publicBuckets;
    }
}