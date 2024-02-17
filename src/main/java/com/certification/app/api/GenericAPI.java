package com.certification.app.api;

import com.certification.app.exception.data.ExpectedError;
import com.certification.app.exception.data.ProjectError;
import com.certification.app.exception.data.UnexpectedError;
import com.certification.app.exception.web.CustomWebException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface GenericAPI<T> {

    default Mono<ResponseEntity<T>> getSuccessResponseEntity(Mono<T> publisher, String errorMessageIfEmpty) {
        return publisher
                .map(this::getSuccessResponseEntity)
                .switchIfEmpty(Mono.error(() -> new CustomWebException(errorMessageIfEmpty, UUID.randomUUID().toString(), HttpStatus.NOT_FOUND)));
    }

    default ResponseEntity<Flux<T>> getSuccessResponseEntityFlux(Flux<T> dto) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto);
    }

    default Mono<ResponseEntity<T>> mapError(ProjectError error, String errorMessage) {
        return switch (error) {
            case ExpectedError.NotFoundError e ->
                    Mono.error(() -> new CustomWebException(errorMessage, UUID.randomUUID().toString(), HttpStatus.NOT_FOUND));

            case UnexpectedError.DatabaseError e -> Mono.error(() -> new RuntimeException("Ocurrio algo mal"));

            case UnexpectedError.ServiceError e -> Mono.error(() -> new RuntimeException("No sabemos que paso"));
        };
    }

    private ResponseEntity<T> getSuccessResponseEntity(T body) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(body);
    }

}
