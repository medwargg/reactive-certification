package com.certiication.app.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebPropertiesConfig {

    @Bean
    public WebProperties.Resources webPropertiesResources(){
        return new WebProperties.Resources();
    }

}
