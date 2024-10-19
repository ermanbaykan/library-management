package co.uk.e2b.identitye2e.assessment.librarymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Book Already Exist")
public class BookAlreadyExist extends RuntimeException {

  public BookAlreadyExist(String message) {
    super(message);
  }
}
