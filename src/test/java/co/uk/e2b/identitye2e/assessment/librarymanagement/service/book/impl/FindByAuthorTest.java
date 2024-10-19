package co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.impl;

import static org.junit.jupiter.api.Assertions.*;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.FindListBy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindByAuthorTest {

  ArrayList<Book> books;
  FindListBy<Book> bookFindBy;

  @BeforeEach
  void setUp() {
    books = new ArrayList<>();
    books.addAll(
        List.of(Book.builder()
                .title("book1")
                .isbn("ISBN1")
                .author("auth1").build(),
            Book.builder()
                .title("book2")
                .isbn("ISBN1")
                .author("auth2 auth2").build()));
  }


  @Test
  void shouldFindBookByAuthorName() {
    bookFindBy = new FindByAuthor();
    var foundBook = bookFindBy.find(books, "auth1");
    assertNotNull(foundBook);
  }

  @Test
  void shouldFindBookByAuthorNameWithSpace() {
    bookFindBy = new FindByAuthor();
    var foundBook = bookFindBy.find(books, "auth2 auth2");
    assertNotNull(foundBook);
    assertEquals(1, foundBook.size());
  }

  @Test
  void shouldReturnNullIfNoBookFoundForAuthorName() {
    bookFindBy = new FindByAuthor();
    var foundBook = bookFindBy.find(books, "auth3");
    assertNull(foundBook);
  }
}
