package com.certification.app.service.generic;

import com.certification.app.exception.data.ProjectError;
import io.vavr.control.Either;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CRUDService<T, ID> {

    Either<ProjectError, Mono<T>> getById(ID id);

    Either<ProjectError, Mono<T>> save(T document);

    Either<ProjectError, Flux<T>> getAll();

}
