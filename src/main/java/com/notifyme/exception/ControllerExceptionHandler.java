package com.notifyme.exception;

import com.notifyme.model.Error;
import com.notifyme.model.Errors;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Errors> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        Errors errors  = new Errors();
        Error error = new Error(HttpStatus.NOT_FOUND.toString(), ex.getMessage());
        errors.addErrorItem(error);
        return new ResponseEntity<Errors>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Errors> handleFeignStatusException(Exception e) {
        log.error("Got unexpected internal server error when processing request", e);

        Error error = new Error();
        error.setCode("99");
        error.setMessage(e.getMessage());

        Errors errors = new Errors();
        errors.addErrorItem(error);

        return new ResponseEntity<Errors>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Errors> missingServletRequestParameterException(MissingServletRequestParameterException e) {

        Errors erros = new Errors();

        Error error = new Error();

        error.setMessage(e.getParameterName() + ": cannot be null");
        error.setCode("NotNull");
        error.setField(e.getParameterName());
        erros.addErrorItem(error);

        return new ResponseEntity<Errors>(erros, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Errors> missingServletRequestHeaderException(MissingRequestHeaderException e) {

        Errors erros = new Errors();

        Error error = new Error();

        error.setMessage(e.getHeaderName() + ": cannot be null");
        error.setCode("NotNull");
        error.setField(e.getHeaderName());
        erros.addErrorItem(error);

        return new ResponseEntity<Errors>(erros, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handleArgumentNotValid(MethodArgumentNotValidException e) {

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        if (!fieldErrors.isEmpty()) {
            Errors errors = new Errors();

            fieldErrors.forEach(fieldError -> {
                Error error = new Error();
                String errorField = fieldError.getField();

                error.setCode(fieldError.getCode());
                error.setField(errorField);
                error.setMessage(
                        (errorField != null && !errorField.isBlank())
                                ? errorField + " " + fieldError.getDefaultMessage()
                                : fieldError.getDefaultMessage()
                );

                errors.addErrorItem(error);
            });

            return ResponseEntity.badRequest().body(errors);
        }

        return defaultResponseError(e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handler(ConstraintViolationException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.toString());

        return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Errors> defaultResponseError(Exception e) {
        log.error("Got unexpected error when processing request", e);

        Error error = new Error();
        error.setCode("99");
        error.setMessage(e.getMessage());

        Errors errors = new Errors();
        errors.addErrorItem(error);

        return new ResponseEntity<Errors>(errors, HttpStatus.BAD_REQUEST);
    }
}
