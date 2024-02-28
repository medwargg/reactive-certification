package com.certification.app.handler;

import com.certification.app.dto.StudentDTO;
import com.certification.app.exception.data.ExpectedError;
import com.certification.app.exception.data.ProjectError;
import com.certification.app.exception.data.UnexpectedError;
import com.certification.app.mapper.StudentMapper;
import com.certification.app.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StudentHandler {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentService.getAll()
                        .map(reactiveStudents -> reactiveStudents.map(studentMapper::toDto))
                        .fold(this::mapErrorFlux, Flux::next), StudentDTO.class);
    }

    private Mono<StudentDTO> mapErrorFlux(ProjectError error) {
        return switch (error) {
            case ExpectedError.NotFoundError e -> Mono.error(() -> new RuntimeException("No se encontraron elementos"));

            case UnexpectedError.DatabaseError e -> Mono.error(() -> new RuntimeException("Ocurrio un error interno"));

            case UnexpectedError.ServiceError e -> Mono.error(() -> new RuntimeException("Ejemplo"));
        };
    }

}
