package com.shelter.ua.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ExceptionResponse {
    private String message;

    @JsonIgnore
    private String timeStamp;

    @JsonIgnore
    private String trace;

    @JsonIgnore
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    public ExceptionResponse(Map<String, Object> errorAttributes) {
        this.setPath((String) errorAttributes.get("path"));
        this.setMessage((String) errorAttributes.get("message"));
        this.setTimeStamp(errorAttributes.get("timestamp").toString());
        this.setTrace((String) errorAttributes.get("trace"));
    }

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public static class Builder {
        private ExceptionResponse response;

        public Builder() {
        }

        public Builder errorAttributes(Map<String, Object> errorAttributes) {
            response.setPath((String) errorAttributes.get("path"));
            response.setMessage((String) errorAttributes.get("message"));
            response.setTimeStamp(errorAttributes.get("timestamp").toString());
            response.setTrace((String) errorAttributes.get("trace"));
            return this;
        }

        public Builder message(String message) {
            response.message = message;
            return this;
        }

        public Builder errors(List<String> errors) {
            response.errors = errors;
            return this;
        }

        public ExceptionResponse build() {
            return response;
        }
    }
}
