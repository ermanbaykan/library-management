package co.uk.e2b.identitye2e.assessment.librarymanagement.dto;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Data
@Builder
@Validated
public class Book {
  @NonNull String isbn;
  String title;
  String author;
  int publicationYear;
  AtomicInteger availableCopies;
}
