package senkondratev.demos.DemoSetronica.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import senkondratev.demos.DemoSetronica.exception.KeyConstrainViolatonException;
import senkondratev.demos.DemoSetronica.exception.NoRelatedDataException;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Configuration
@RestControllerAdvice
public class ProductExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ProductExceptionHandler.class);

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<CustomError> onNoSuchElementException(
            NoSuchElementException e) {
        CustomError error = new CustomError(ErrorCode.NO_SUCH_ID, e.getMessage());
        logger.info(String.format("NoSuchElementException was handled. Exception message: %s. \n " +
                "Exception stack trace: %s", e.getMessage(), Arrays.toString(e.getStackTrace())));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmptyResultDataAccessException.class)
    public ResponseEntity<CustomError> onEmptyResultDataAccessException(
            EmptyResultDataAccessException e) {
        CustomError error = new CustomError(ErrorCode.CANNOT_DELETE, "Unable to delete entity with provided ID: no such entity");
        logger.info(String.format("EmptyResultDataAccessException was handled. Exception message: %s. \n " +
                "Exception stack trace: %s", e.getMessage(), Arrays.toString(e.getStackTrace())));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value =  MethodArgumentNotValidException.class)
    public ResponseEntity<CustomError> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (org.springframework.validation.FieldError fieldError: fieldErrors) {
            sb.append("Problematic field: ")
                    .append(fieldError.getField())
                    .append(". Message: ")
                    .append(fieldError.getDefaultMessage())
                    .append(System.lineSeparator());
        }
        CustomError error = new CustomError(ErrorCode.VALIDATION_FAILED, sb.toString());
        logger.info(String.format("MethodArgumentNotValidException. Exception message: %s. \n " +
                "Exception stack trace: %s", e.getMessage(), Arrays.toString(e.getStackTrace())));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = KeyConstrainViolatonException.class)
    public ResponseEntity<CustomError> onConstraintException(
            KeyConstrainViolatonException e) {
        CustomError error = new CustomError(ErrorCode.CONSTRAINT_VIOLATION, e.getMessage());
        logger.info(String.format("EmptyResultDataAccessException was handled. Exception message: %s. \n " +
                "Exception stack trace: %s", e.getMessage(), Arrays.toString(e.getStackTrace())));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoRelatedDataException.class)
    public ResponseEntity<CustomError> onNoRelatedDataException(NoRelatedDataException e){
        CustomError error = new CustomError(ErrorCode.BAD_USER_REQUEST, e.getMessage());
        logger.info(String.format("NoRelatedDataException was handled. Exception message: %s. \n " +
                "Exception stack trace: %s", e.getMessage(), Arrays.toString(e.getStackTrace())));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<CustomError> onIllegalArgumentException(IllegalArgumentException e){
        CustomError error = new CustomError(ErrorCode.ILLEGAL_ARGUMENT, e.getMessage());
        logger.info(String.format("IllegalArgumentException was handled. Exception message: %s. \n " +
                "Exception stack trace: %s", e.getMessage(), Arrays.toString(e.getStackTrace())));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
