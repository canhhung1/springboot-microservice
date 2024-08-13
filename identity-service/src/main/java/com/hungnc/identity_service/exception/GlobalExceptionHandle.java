package com.hungnc.identity_service.exception;

import com.hungnc.identity_service.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(1001);
        apiResponse.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setCode(e.getErrorCode().getCode());
        apiResponse.setMessage(e.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
