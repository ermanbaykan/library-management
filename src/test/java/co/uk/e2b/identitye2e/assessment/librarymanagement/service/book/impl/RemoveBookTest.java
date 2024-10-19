package co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.impl;

import static org.junit.jupiter.api.Assertions.*;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemoveBookTest {

  ArrayList<Book> books;
  RemoveBook removeBook;

  @BeforeEach
  void setUp() {
    removeBook = new RemoveBook();
    books = new ArrayList<>();
    books.addAll(
        List.of(Book.builder().title("book1").isbn("ISBN1").author("auth1").build(),
            Book.builder().title("book2").isbn("ISBN1").author("auth2").build()));
  }

  @Test
  void shouldRemoveBook() {
    var removableBook = Book.builder().title("book1").isbn("ISBN1").author("auth1").build();
    assertEquals(2,books.size());
    var newList = removeBook.remove(books, removableBook);
    assertEquals(1,newList.size());
  }
  @Test
  void shouldNotRemoveBook() {
    var removableBook = Book.builder().title("book1").isbn("ISBN3").author("auth1").build();
    assertEquals(2,books.size());
    var newList = removeBook.remove(books, removableBook);
    assertEquals(2,newList.size());
  }
}
