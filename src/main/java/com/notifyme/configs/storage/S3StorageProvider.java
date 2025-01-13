package com.notifyme.configs.storage;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.notifyme.configs.properties.S3StorageServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class S3StorageProvider {

    private final S3StorageServiceProperties storageServiceProperties;

    @Bean
    @Primary
    public AmazonS3 createS3Client() {
        if (StringUtils.isNotBlank(storageServiceProperties.getEndpointUrl())) {
            log.info("Creating storage client for URL: {}", storageServiceProperties.getEndpointUrl());
            return endpointBasedS3Client();
        }

        String region = "br-se1";
        log.info("Creating standard s3 client to {} region", region);
        return AmazonS3ClientBuilder.standard().withRegion(region).build();
    }

    private AmazonS3 endpointBasedS3Client() {
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(storageServiceProperties.getEndpointUrl(), "auto");
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(storageServiceProperties.getAccessKey(), storageServiceProperties.getSecretKey());
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(basicAWSCredentials);

        return AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(endpointConfiguration)
                .withCredentials(credentialsProvider)
                .build();
    }

}