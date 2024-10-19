package co.uk.e2b.identitye2e.assessment.librarymanagement.dto;

import co.uk.e2b.identitye2e.assessment.librarymanagement.exception.BookAlreadyExist;
import co.uk.e2b.identitye2e.assessment.librarymanagement.exception.BookNotFound;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BookNotFound.class)
  public ResponseEntity<Object> handleNotFound(Exception ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }
  @ExceptionHandler(BookAlreadyExist.class)
  public ResponseEntity<Object> handleAlreadyExist(Exception ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}