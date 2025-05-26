package dev.ionut.jobify.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class ApiResponse<T> {

    private String message;

    private int status;

    private T data;

    public ApiResponse(String message, HttpStatus httpStatus) {
        this(message, httpStatus, null);
    }

    public ApiResponse(String message, HttpStatus httpStatus, T data) {
        this.message = message;
        this.status = httpStatus.value();
        this.data = data;
    }
}