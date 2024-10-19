package co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.impl;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.FindBy;
import java.util.List;

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
