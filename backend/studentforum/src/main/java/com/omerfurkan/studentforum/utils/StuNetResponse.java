package com.omerfurkan.studentforum.utils;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Collections;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@JsonPropertyOrder({"headers", "message", "status", "data", "params"})
public class StuNetResponse<T> {
    private HttpHeaders headers;
    private String message;
    private int status;
    private T data;
    private Map<String, Object> params;

    public StuNetResponse(StuNetResponseBuilder<T> builder) {
        this.headers = builder.headers;
        this.message = builder.message;
        this.status = builder.status;
        this.data = builder.data;
        this.params = builder.params;

    }

    public static class StuNetResponseBuilder<T> {
        private HttpHeaders headers = new HttpHeaders();
        private final int status;
        private final String message;
        private T data;
        private Map<String, Object> params = Collections.emptyMap();

        public StuNetResponseBuilder(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public StuNetResponseBuilder<T> addData(T data) {
            this.data = data;
            return this;
        }

        public StuNetResponseBuilder<T> addParams(Map<String, Object> params) {
            this.params = params;
            return this;
        }

        public StuNetResponseBuilder<T> addHeader(HttpHeaders headers) {
            this.headers = headers;
            return this;
        }

        public ResponseEntity<StuNetResponse> build() {
            StuNetResponse<T> stuNetResponse = new StuNetResponse<>(this);
            return ResponseEntity.status(status).headers(headers).body(stuNetResponse);
        }

    }

}
