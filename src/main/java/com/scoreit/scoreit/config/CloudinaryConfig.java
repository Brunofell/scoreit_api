package com.scoreit.scoreit.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", "dofqi7fge");
        config.put("api_key", "729134971439599");
        config.put("api_secret", "m5zeaTFSM7mbRVkySqBqTR8cSDc");
        return new Cloudinary(config);
    }
}
