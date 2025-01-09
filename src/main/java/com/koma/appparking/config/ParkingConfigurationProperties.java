package com.koma.appparking.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@ConfigurationProperties(prefix = "parking.default")
@Getter
@Setter
public class ParkingConfigurationProperties {
    private BigDecimal hourlyRate;
}
