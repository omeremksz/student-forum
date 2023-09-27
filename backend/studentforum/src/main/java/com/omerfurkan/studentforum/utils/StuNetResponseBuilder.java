package com.omerfurkan.studentforum.utils;

import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class StuNetResponseBuilder {

    public ResponseEntity<StuNetResponse> buildResponse(HttpHeaders headers, String message, int status, Object data, Map<String, Object> params) {
        return new StuNetResponse.StuNetResponseBuilder<>(status, message)
            .addData(data)
            .addParams(params)
            .addHeader(headers)
            .build();
    }

    public ResponseEntity<StuNetResponse> buildResponse(HttpStatus status, String message, Object data, Map<String, Object> params) {
        return new StuNetResponse.StuNetResponseBuilder<>(status.value(), message)
            .addData(data)
            .addParams(params)
            .build();
    }

    public ResponseEntity<StuNetResponse> buildResponse(HttpStatus status, String message, Object data) {
        return new StuNetResponse.StuNetResponseBuilder<>(status.value(), message)
            .addData(data)
            .build();
    }

    public ResponseEntity<StuNetResponse> buildResponse(HttpStatus status, String message) {
        return new StuNetResponse.StuNetResponseBuilder<>(status.value(), message)
            .build();
    }

    public ResponseEntity<StuNetResponse> buildResponse(HttpStatus status, String message, Map<String, Object> params) {
        return new StuNetResponse.StuNetResponseBuilder<>(status.value(), message)
            .addParams(params)
            .build();
    }

    public ResponseEntity<StuNetResponse> buildResponse(HttpStatus status, String message, HttpHeaders headers) {
        return new StuNetResponse.StuNetResponseBuilder<>(status.value(), message)
            .addHeader(headers)
            .build();
    }

    public ResponseEntity<StuNetResponse> buildResponse(HttpStatus status, String message, Object data, HttpHeaders headers) {
        return new StuNetResponse.StuNetResponseBuilder<>(status.value(), message)
            .addData(data)
            .addHeader(headers)
            .build();
    }

    public ResponseEntity<StuNetResponse> buildResponse(HttpStatus status, String message, HttpHeaders headers, Map<String, Object> params) {
        return new StuNetResponse.StuNetResponseBuilder<>(status.value(), message)
            .addHeader(headers)
            .addParams(params)
            .build();
    }

}
