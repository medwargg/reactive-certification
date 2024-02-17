package com.certification.app.api;

import com.certification.app.constant.ResourceConstant;
import com.certification.app.dto.StudentDTO;
import com.certification.app.exception.data.ExpectedError;
import com.certification.app.exception.data.ProjectError;
import com.certification.app.exception.data.UnexpectedError;
import com.certification.app.mapper.StudentMapper;
import com.certification.app.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.certification.app.constant.ResourceConstant.BY_ID;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ResourceConstant.STUDENTS_API)
public class StudentController implements GenericAPI<StudentDTO> {

    private final StudentService studentService;
    private final StudentMapper studentMapper;

    @GetMapping(path = BY_ID)
    public Mono<ResponseEntity<StudentDTO>> getById(@PathVariable("id") String id) {
        return studentService
                .getById(id) // Either<ProjectError, Mono<Student>>
                .map(success -> success.map(studentMapper::toDto)) // Either<Mono<ResponseEntity<StudentDTO>>, Mono<StudentDTO>>
                .fold(failure -> mapError(failure, "El estudiante no fue encontrado."), success -> getSuccessResponseEntity(success, "El estudiante no fue encontrado."));
    }

    @PostMapping
    public Mono<ResponseEntity<StudentDTO>> create(@RequestBody StudentDTO studentDTO, ServerHttpRequest request) {
        return studentService
                .save(studentMapper.toDocument(studentDTO))
                .map(reactiveStudent -> reactiveStudent.map(studentMapper::toDto))
                .fold(failure -> mapError(failure, "El estudiante no fue encontrado."), success -> success.map(dto -> getCreated(request, dto)));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<StudentDTO>>> getAll() {
        return studentService
                .getAll()
                .map(reactiveStudents -> reactiveStudents.map(studentMapper::toDto))
                .fold(this::mapErrorFlux, success -> Mono.just(getSuccessResponseEntityFlux(success)));
    }

    private Mono<ResponseEntity<Flux<StudentDTO>>> mapErrorFlux(ProjectError error) {
        return switch (error) {
            case ExpectedError.NotFoundError e -> Mono.error(() -> new RuntimeException("No se encontraron elementos"));

            case UnexpectedError.DatabaseError e -> Mono.error(() -> new RuntimeException("Ocurrio un error interno"));

            case UnexpectedError.ServiceError e -> Mono.error(() -> new RuntimeException("Ejemplo"));
        };
    }

    private ResponseEntity<StudentDTO> getCreated(ServerHttpRequest request, StudentDTO studentDTO) {
        return ResponseEntity
                .created(URI.create(String.join("/", request.getURI().toString(), studentDTO.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(studentDTO);
    }

}
