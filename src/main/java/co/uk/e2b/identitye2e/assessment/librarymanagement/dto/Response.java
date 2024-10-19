package co.uk.e2b.identitye2e.assessment.librarymanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

  String message;
  List<Book> books;
}
