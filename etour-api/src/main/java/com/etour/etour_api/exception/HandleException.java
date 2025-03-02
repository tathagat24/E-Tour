package com.etour.etour_api.exception;

import com.etour.etour_api.payload.response.Response;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.*;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static com.etour.etour_api.utils.RequestUtils.handleErrorResponse;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCauseMessage;
import static org.springframework.http.HttpStatus.*;

/**
 * @version 1.0
 * @project etour-api
 * @since 28-01-2025
 */

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class HandleException extends ResponseEntityExceptionHandler implements ErrorController {
    private final HttpServletRequest request;

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception exception, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest webRequest) {
        log.error("handleExceptionInternal: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(exception.getMessage(), getRootCauseMessage(exception), request, statusCode), statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, @NonNull HttpHeaders headers, @NonNull HttpStatusCode statusCode, @NonNull WebRequest webRequest) {
        log.error("handleMethodArgumentNotValid: {}", exception.getMessage());
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(joining(", "));
        return new ResponseEntity<>(handleErrorResponse(fieldMessage, getRootCauseMessage(exception), request, statusCode), statusCode);
    }

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Response> apiException(ApiException exception) {
        log.error("ApiException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(exception.getMessage(), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    public ResponseEntity<Response> badCredentialsException(BadCredentialsException exception) {
        log.error("BadCredentialsException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(exception.getMessage(), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Response> sqlIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {
        log.error("SQLIntegrityConstraintViolationException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(exception.getMessage().contains("Duplicate entry") ? "Information already exists" : exception.getMessage(), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = UnrecognizedPropertyException.class)
    public ResponseEntity<Response> unrecognizedPropertyException(UnrecognizedPropertyException exception) {
        log.error("UnrecognizedPropertyException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(exception.getMessage(), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Response> accessDeniedException(AccessDeniedException exception) {
        log.error("AccessDeniedException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse("Access denied. You don't have access", getRootCauseMessage(exception), request, FORBIDDEN), FORBIDDEN);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Response> exception(Exception exception) {
        log.error("Exception: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(processErrorMessage(exception), getRootCauseMessage(exception), request, INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = TransactionSystemException.class)
    public ResponseEntity<Response> transactionSystemException(TransactionSystemException exception) {
        log.error("TransactionSystemException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(processErrorMessage(exception), getRootCauseMessage(exception), request, INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<Response> emptyResultDataAccessException(EmptyResultDataAccessException exception) {
        log.error("EmptyResultDataAccessException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(exception.getMessage(), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = CredentialsExpiredException.class)
    public ResponseEntity<Response> credentialsExpiredException(CredentialsExpiredException exception) {
        log.error("CredentialsExpiredException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(exception.getMessage(), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = DisabledException.class)
    public ResponseEntity<Response> disabledException(DisabledException exception) {
        log.error("DisabledException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse("User account is currently disabled", getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = LockedException.class)
    public ResponseEntity<Response> lockedException(LockedException exception) {
        log.error("LockedException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(exception.getMessage(), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = AccountExpiredException.class)
    public ResponseEntity<Response> accountExpiredException(AccountExpiredException exception) {
        log.error("AccountExpiredException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(exception.getMessage(), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseEntity<Response> duplicateKeyException(DuplicateKeyException exception) {
        log.error("DuplicateKeyException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(processErrorMessage(exception), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Response> dataIntegrityViolationException(DataIntegrityViolationException exception) {
        log.error("DataIntegrityViolationException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(processErrorMessage(exception), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(value = DataAccessException.class)
    public ResponseEntity<Response> dataAccessException(DataAccessException exception) {
        log.error("DataAccessException: {}", exception.getMessage());
        return new ResponseEntity<>(handleErrorResponse(processErrorMessage(exception), getRootCauseMessage(exception), request, BAD_REQUEST), BAD_REQUEST);
    }

    private String processErrorMessage(Exception exception) {
        if(exception instanceof ApiException) { return exception.getMessage(); }
        //if(exception instanceof TransactionSystemException) { return getRootCauseMessage(exception).split(":")[1]; }
        if (exception.getMessage() != null) {
            if (exception.getMessage().contains("duplicate") && exception.getMessage().contains("AccountVerifications")) {
                return "You already verified your account.";
            }
            if (exception.getMessage().contains("duplicate") && exception.getMessage().contains("ResetPasswordVerifications")) {
                return "We already sent you an email to reset your password.";
            }
            if (exception.getMessage().contains("duplicate") && exception.getMessage().contains("Key (email)")) {
                return "Email already exists. Use a different email and try again.";
            }
            if (exception.getMessage().contains("duplicate")) {
                return "Duplicate entry. Please try again.";
            }
        }
        return "An error occurred. Please try again.";
    }
}

