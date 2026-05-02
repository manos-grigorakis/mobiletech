package com.mgrigorakis.mobiletech.common.exception;

import com.mgrigorakis.mobiletech.common.dto.ApiResponse;
import com.mgrigorakis.mobiletech.common.dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {
    // Field Validation Error - 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> details = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> details.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Field Validation Failed",
                "FIELD_VALIDATION_FAILED",
                details
        );

        return new ResponseEntity<>(new ApiResponse<>(errorResponse), HttpStatus.BAD_REQUEST);
    }

    // Not Found - 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null,
                null
        );

        log.error(ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(errorResponse), HttpStatus.NOT_FOUND);
    }

    // Conflict Exception Error - 409
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflict(ConflictException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                ex.getErrorCode(),
                null
        );

        log.error(ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(errorResponse), HttpStatus.CONFLICT);
    }

    // Server Error - 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                null,
                null
        );

        log.error(ex.getMessage());
        return new ResponseEntity<>(new ApiResponse<>(errorResponse), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
