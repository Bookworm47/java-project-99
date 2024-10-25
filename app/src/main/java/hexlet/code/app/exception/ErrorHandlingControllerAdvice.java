package hexlet.code.app.exception;

import hexlet.code.app.exception.validation.ValidationErrorResponse;
import hexlet.code.app.exception.validation.Violation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Controller advice for handling errors.
 */
@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    /**
     * Handles ConstraintViolationException.
     * <p>
     * This method can be overridden to provide custom handling for constraint violations.
     *
     * @param e the ConstraintViolationException instance
     * @return a ValidationErrorResponse instance
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(violation -> new Violation(
                        violation.getPropertyPath().toString(),
                        violation.getMessage()))
                .toList();

        return new ValidationErrorResponse(violations);
    }

    /**
     * Handles MethodArgumentNotValidException.
     * <p>
     * This method can be overridden to provide custom handling for method argument validation errors.
     *
     * @param e the MethodArgumentNotValidException instance
     * @return a ValidationErrorResponse instance
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        return new ValidationErrorResponse(violations);
    }
}
