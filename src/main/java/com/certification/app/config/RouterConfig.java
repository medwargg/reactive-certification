package com.certification.app.config;

import com.certification.app.dto.StudentDTO;
import com.certification.app.handler.StudentHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    @RouterOperations(
            {
                    @RouterOperation(path = "/reactive/api/functional/students/", produces = {
                            MediaType.APPLICATION_JSON_VALUE},
                            beanClass = StudentHandler.class, method = RequestMethod.GET, beanMethod = "findAll",
                            operation = @Operation(operationId = "findAll", responses = {
                                    @ApiResponse(responseCode = "200", description = "successful operation",
                                            content = @Content(schema = @Schema(implementation = StudentDTO.class))),
                                    @ApiResponse(responseCode = "400", description = "Invalid Employee details supplied")}
                            ))

            })
    RouterFunction<ServerResponse> routes(StudentHandler handler) {
        return route(GET("/reactive/api/functional/students/"), handler::findAll);
    }

}
