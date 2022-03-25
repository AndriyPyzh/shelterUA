package com.shelter.ua.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
@Valid
@Configuration
@ConfigurationProperties(prefix = "shelter.ua")
public class JwtProperties {

    @NotBlank
    private String secretKey;

}

