package com.shahrozz.demo.AWS;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.SecurityGroup;
import software.amazon.awssdk.services.ec2.Ec2Client;
import java.util.List;
import java.util.stream.Collectors;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class AwsScannerService {

    @Autowired
    private AmazonEC2 ec2Client;

    public List<SecurityGroup> checkInsecureSecurityGroups() {
        return ec2Client.describeSecurityGroups().securityGroups().stream()
                .filter(sg -> sg.ipPermissions().stream()
                        .anyMatch(perm -> perm.ipRanges().stream()
                                .anyMatch(cidr -> cidr.equals("0.0.0.0/0"))
                        )
                )
                .collect(Collectors.toList());
    }
}
