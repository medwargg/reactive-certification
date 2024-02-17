package com.certification.app.exception.data;

public sealed interface ProjectError permits UnexpectedError, ExpectedError {
}
