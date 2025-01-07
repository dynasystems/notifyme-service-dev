package com.notifyme.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "notifyme.notification-email")
public class ScheduleNotificationProperties {

    private boolean active;
    private Integer verificationInTimeSeconds;

    public Integer verificationInTimeSeconds() {
        return verificationInTimeSeconds < 45 ? 45 : verificationInTimeSeconds;
    }
}
