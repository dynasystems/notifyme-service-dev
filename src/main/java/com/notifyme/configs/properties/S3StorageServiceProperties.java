package com.notifyme.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import static io.micrometer.common.util.StringUtils.isBlank;

@Data
@Validated
@Component
@ConfigurationProperties("s3storage-service")
public class S3StorageServiceProperties implements Validator {

    private static final String NOT_BLANK_MESSAGE = "Value cannot be null or blank.";
    private static final String NOT_BLANK_CODE = "NotBlank";

    private String endpointUrl;
    private String accessKey;
    private String secretKey;

    @Override
    public boolean supports(Class<?> clazz) {
        return S3StorageServiceProperties.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final S3StorageServiceProperties storageServiceProperties = (S3StorageServiceProperties) target;
        if (isBlank(storageServiceProperties.endpointUrl)) {
            return;
        }
        if (isBlank(accessKey)) {
            errors.rejectValue("access-key", NOT_BLANK_CODE, NOT_BLANK_MESSAGE);
        }
        if (isBlank(secretKey)) {
            errors.rejectValue("secret-key", NOT_BLANK_CODE, NOT_BLANK_MESSAGE);
        }
    }

}