package com.certification.app.service.impl;

import com.certification.app.exception.data.ProjectError;
import com.certification.app.exception.data.UnexpectedError.DatabaseError;
import com.certification.app.repository.generic.CRUDRepository;
import com.certification.app.service.generic.CRUDService;
import io.vavr.control.Either;
import io.vavr.control.Try;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public abstract class CRUDServiceImpl<T, ID> implements CRUDService<T, ID> {

    protected abstract CRUDRepository<T, ID> getMainRepository();

    @Override
    public Either<ProjectError, Mono<T>> getById(ID id) {
        return Try.of(() -> getMainRepository().findById(id))
                .toEither()
                .mapLeft(DatabaseError::new);
    }

    @Override
    public Either<ProjectError, Mono<T>> save(T t) {
        return Try.of(() -> getMainRepository().save(t))
                .toEither()
                .mapLeft(DatabaseError::new);
    }

    @Override
    public Either<ProjectError, Flux<T>> getAll() {
        return Try.of(() -> getMainRepository().findAll())
                .toEither()
                .mapLeft(DatabaseError::new);
    }
}
