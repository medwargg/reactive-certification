package com.certification.app.exception.data;

public sealed interface UnexpectedError extends ProjectError {

    record DatabaseError(Throwable cause) implements UnexpectedError { }
    record ServiceError(Throwable cause) implements UnexpectedError { }

}
