package co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.impl;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.FindListBy;
import java.util.List;

public class FindByAuthor implements FindListBy<Book> {

  @Override
  public List<Book> find(List<Book> items, String authorName) {
    var found = items.stream().filter(book -> book.getAuthor().equals(authorName)).toList();
    return !found.isEmpty() ? found : null;
  }
}
