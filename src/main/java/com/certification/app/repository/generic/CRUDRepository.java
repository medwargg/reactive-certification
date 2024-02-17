package com.certification.app.repository.generic;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

// Excludes this class as a spring bean due to is extending from ReactiveMongoRepository.
@NoRepositoryBean
public interface CRUDRepository<T, ID> extends ReactiveMongoRepository<T, ID> {

}
