package co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.impl;

import static org.junit.jupiter.api.Assertions.*;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.FindBy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindByISBNTest {

  ArrayList<Book> books;
  FindBy<Book> bookFindBy;

  @BeforeEach
  void setUp() {
    books = new ArrayList<>();
    books.addAll(
        List.of(
            Book.builder().title("book1").isbn("ISBN1").author("auth1").build(),
            Book.builder().title("book2").isbn("ISBN1").author("auth2").build()));
  }

  @Test
  void shouldFindBookByISBN() {
    bookFindBy = new FindByISBN();
    var foundBook = bookFindBy.find(books, "ISBN1");
    assertNotNull(foundBook);
  }

  @Test
  void shouldReturnNullIfNoBookFoundForISBN() {
    bookFindBy = new FindByISBN();
    var foundBook = bookFindBy.find(books, "ISBN3");
    assertNull(foundBook);
  }
}
