package co.uk.e2b.identitye2e.assessment.librarymanagement.service;

import static org.junit.jupiter.api.Assertions.*;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.enums.ResultType;
import co.uk.e2b.identitye2e.assessment.librarymanagement.exception.BookAlreadyExist;
import co.uk.e2b.identitye2e.assessment.librarymanagement.exception.BookNotFound;
import org.junit.jupiter.api.Test;

class LibraryTest {

  @Test
  void shouldAddBook() {
    Library library = new Library();
    var res = library.addBook(Book.builder().title("book1").isbn("ISBN1").author("auth1").build());
    assertEquals(ResultType.ADDED, res);
  }

  @Test
  void shouldThrowExceptionAddingSameBook() {
    Library library = new Library();
    var res = library.addBook(Book.builder().title("book1").isbn("ISBN1").author("auth1").build());
    assertThrows(
        BookAlreadyExist.class,
        () -> {
          library.addBook(Book.builder().title("book1").isbn("ISBN1").author("auth1").build());
        });
  }

  @Test
  void removeBook() {
    Library library = new Library();
    library.addBook(Book.builder().title("book1").isbn("ISBN1").author("auth1").build());
    var res = library.removeBook("ISBN1");
    assertEquals(ResultType.REMOVED, res);
  }

  @Test
  void findBookByISBN() {
    Library library = new Library();
    library.addBook(Book.builder().title("book1").isbn("ISBN1").author("auth1").build());
    var res = library.findBookByISBN("ISBN1");
    assertNotNull(res);
  }
  @Test
  void shouldThrowNotFoundExceptionForISBN() {
    Library library = new Library();
    library.addBook(Book.builder().title("book1").isbn("ISBN1").author("auth1").build());
    assertThrows(
        BookNotFound.class,
        () -> {
          library.findBookByISBN("ISBN2");
        });
  }

  @Test
  void findBooksByAuthor() {
    Library library = new Library();
    library.addBook(Book.builder().title("book1").isbn("ISBN1").author("auth1").build());
    library.addBook(Book.builder().title("book2").isbn("ISBN2").author("auth1").build());
    var res = library.findBooksByAuthor("auth1");
    assertNotNull(res);
    assertEquals(2, res.size());
  }
  @Test
  void shouldThrowNotFoundExceptionForAuthor() {
    Library library = new Library();
    library.addBook(Book.builder().title("book2").isbn("ISBN2").author("auth1").build());
    assertThrows(
        BookNotFound.class,
        () -> {
          library.findBooksByAuthor("auth2");
        });
  }

  @Test
  void borrowBook() {
    Library library = new Library();
    library.addBook(Book.builder().title("book2").isbn("ISBN1").author("auth1").build());
    var res = library.borrowBook("ISBN1");
    var book = library.findBookByISBN("ISBN1");
    assertNotNull(res);
    assertEquals(0, book.getAvailableCopies().intValue());
  }
  @Test
  void shouldThrowErrorBorrowBook() {
    Library library = new Library();
    library.addBook(Book.builder().title("book2").isbn("ISBN2").author("auth1").build());
    assertThrows(
        BookNotFound.class,
        () -> {
          library.borrowBook("ISBN1");
        });
  }

  @Test
  void returnBook() {
    Library library = new Library();
    library.addBook(Book.builder().title("book2").isbn("ISBN2").author("auth1").build());
    var res = library.returnBook("ISBN2");
    var book = library.findBookByISBN("ISBN2");
    assertNotNull(res);
    assertEquals(2, book.getAvailableCopies().intValue());
  }
}
