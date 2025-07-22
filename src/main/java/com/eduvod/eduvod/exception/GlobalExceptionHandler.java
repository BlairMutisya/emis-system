package com.eduvod.eduvod.exception;

import com.eduvod.eduvod.dto.response.common.BaseApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public BaseApiResponse<?> handleNotFound(ResourceNotFoundException ex) {
        return BaseApiResponse.error(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public BaseApiResponse<?> handleBadRequest(BadRequestException ex) {
        return BaseApiResponse.error(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public BaseApiResponse<?> handleConflict(ConflictException ex) {
        return BaseApiResponse.error(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public BaseApiResponse<?> handleForbidden(UnauthorizedActionException ex) {
        return BaseApiResponse.error(HttpStatus.FORBIDDEN.value(), ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseApiResponse<?> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .findFirst()
                .orElse("Invalid request");
        return BaseApiResponse.error(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(Exception.class)
    public BaseApiResponse<?> handleGeneric(Exception ex) {
        return BaseApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong: " + ex.getMessage());
    }
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<BaseApiResponse<String>> handleDuplicateResource(DuplicateResourceException ex) {
        return new ResponseEntity<>(
                BaseApiResponse.error(ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }
    @ExceptionHandler(SchoolImportException.class)
    public ResponseEntity<BaseApiResponse<String>> handleSchoolImportException(SchoolImportException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseApiResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(SchoolExportException.class)
    public ResponseEntity<BaseApiResponse<String>> handleSchoolExportException(SchoolExportException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseApiResponse.error(ex.getMessage()));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<BaseApiResponse<String>> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseApiResponse.error(ex.getMessage()));
    }


}
