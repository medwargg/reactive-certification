package com.certification.app.handler;

import com.certification.app.exception.web.CustomWebException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(-1)
@Slf4j
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext, ServerCodecConfigurer serverCodecConfigurer) {
        super(errorAttributes, resources, applicationContext);

        // Sets the writers for this handler
        this.setMessageWriters(serverCodecConfigurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        // Indicates that all requests' errors will be handled by handleErrorResponse method.
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
        // Gets the default errors structure and build the server response.
        //return buildCustomErrorFrom(getErrorAttributes(serverRequest, ErrorAttributeOptions.defaults()));
        return buildCustomErrorFrom(getError(serverRequest));
    }

    private Mono<ServerResponse> buildCustomErrorFrom(Map<String, Object> originalErrors) {
        Map<String, Object> customErrors = new HashMap<>();
        customErrors.put("error", originalErrors.get("error"));
        customErrors.put("path", originalErrors.get("path"));
        customErrors.put("timestamp", originalErrors.get("timestamp"));

        return ServerResponse.status((Integer) originalErrors.get("status"))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(customErrors));
    }

    private Mono<ServerResponse> buildCustomErrorFrom(Throwable error) {
        Map<String, Object> errorResponse = new HashMap<>();
        int statusCode = 0;

        switch (error) {
            case CustomWebException e -> {
                statusCode = e.getStatus().value();
                errorResponse.put("status", e.getStatus().name());
                errorResponse.put("detail", e.getMessage());
                errorResponse.put("traceId", e.getTraceId());
            }
            case RuntimeException e -> {
                statusCode = 400;
                errorResponse.put("status", 400);
                errorResponse.put("detail", e.getMessage());
            }

            default -> statusCode = 500;
        }

        return buildServerResponse(statusCode, errorResponse);
    }

    private Mono<ServerResponse> buildServerResponse(Integer status, Map<String, Object> customErrors) {
        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(customErrors));
    }

}
