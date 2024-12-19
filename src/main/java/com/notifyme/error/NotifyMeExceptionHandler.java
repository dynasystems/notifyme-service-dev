package com.notifyme.error;

import com.notifyme.error.exceptions.NotifyMeException;
import com.notifyme.error.exceptions.ResourceNotFoundException;
import com.notifyme.model.Error;
import com.notifyme.model.Errors;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@ControllerAdvice
public class NotifyMeExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Errors> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        Errors errors  = new Errors();
        Error error = new Error(HttpStatus.NOT_FOUND.toString(), ex.getMessage());
        errors.addErrorItem(error);
        return new ResponseEntity<Errors>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotifyMeException.class)
    public ResponseEntity<Errors> handleBusinessException(NotifyMeException e) {

        Error error = new Error();
        error.setCode(e.getErrorCode());
        error.setMessage(e.getMessage());

        Errors errors = new Errors();
        errors.addErrorItem(error);

        return new ResponseEntity<Errors>(errors, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Errors> handleConstraintViolationException(ConstraintViolationException e) {
        log.debug("Got ConstraintViolationException when processing request", e);

        if (Stream.of(e.getConstraintViolations()).findAny().isPresent()) {

            Errors errors = new Errors();

            for (ConstraintViolation<?> constraint : e.getConstraintViolations()) {

                String propertyPath = extractPropertyPath(constraint);

                String finalMessage = propertyPath + ": " + constraint.getMessage();

                NotifyMeException businessException = new NotifyMeException(NotifyMeErrorEnum.MISSING_REQUIRED_PARAMETER,finalMessage);

                Error error = new Error();
                error.setCode(businessException.getErrorCode());
                error.setMessage(businessException.getMessage());
                error.setField(propertyPath);

                errors.addErrorItem(error);
            }

            return new ResponseEntity<Errors>(errors, HttpStatus.BAD_REQUEST);

        } else {
            return defaultResponseError(e);
        }
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

    private String extractPropertyPath(ConstraintViolation<?> constraint) {

        String propertyPath = constraint.getPropertyPath() != null ?
                constraint.getPropertyPath().toString() : null;

        String[] splittedPropertyPath = StringUtils.isNotBlank(propertyPath) ? propertyPath.split("\\.") : null;

        if (Stream.of(splittedPropertyPath).findAny().isPresent()) {
            propertyPath = splittedPropertyPath[1];
        }

        return propertyPath;
    }
}
