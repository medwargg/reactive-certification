package com.certification.app.exception.data;

public sealed interface ExpectedError extends ProjectError {

    record NotFoundError(String userId) implements ExpectedError { }

}
