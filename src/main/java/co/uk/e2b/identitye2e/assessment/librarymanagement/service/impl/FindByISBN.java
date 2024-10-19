package co.uk.e2b.identitye2e.assessment.librarymanagement.service.impl;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.FindBy;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FindByISBN implements FindBy<Book> {

  @Override
  public Book find(List<Book> books, String isbn) {
    if (isbn.isEmpty()) {
      return null;
    }
    return books.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst()
        .orElse(null);
  }
}
