package com.oliver.retrofit.exception;

import com.oliver.retrofit.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.oliver.retrofit.utils.Constants.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidCustomException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidDniException(InvalidCustomException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.<Void>builder()
                        .code(CODE_BAD_REQUEST)
                        .message(ex.getMessage())
                        .data(null)
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        return ResponseEntity.status(CODE_INTERNAL_SERVER_ERROR).body(
                ApiResponse.<Void>builder()
                        .code(CODE_INTERNAL_SERVER_ERROR)
                        .message(CODE_INTERNAL_SERVER_ERROR_MSG + ex.getMessage())
                        .data(null)
                        .build()
        );
    }
}
