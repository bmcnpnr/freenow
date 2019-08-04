package com.freenow.security.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data public
class JwtProperties
{

    private String secretKey = "freenow";

    private long validityInMs = 3600000 * 24; // for 24 hours


    String getSecretKey()
    {
        return secretKey;
    }


    long getValidityInMs()
    {
        return validityInMs;
    }

}
