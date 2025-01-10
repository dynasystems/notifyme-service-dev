package com.notifyme.configs.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransferManagerFactoryImpl implements TransferManagerFactory{

    private final AmazonS3 amazonS3;

    @Override
    public TransferManager createTransferManager() {
        return TransferManagerBuilder.standard().withS3Client(amazonS3).build();
    }
}
