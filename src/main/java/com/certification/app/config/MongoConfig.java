package com.certification.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

@Configuration
@RequiredArgsConstructor
public class MongoConfig implements InitializingBean {

    @Lazy
    private final MappingMongoConverter converter;

    @Override
    public void afterPropertiesSet() {
        // This makes mongo to remove the class attribute in the document.
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
    }

}
