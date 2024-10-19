package co.uk.e2b.identitye2e.assessment.librarymanagement.service;

import co.uk.e2b.identitye2e.assessment.librarymanagement.dto.Book;
import co.uk.e2b.identitye2e.assessment.librarymanagement.enums.ResultType;
import co.uk.e2b.identitye2e.assessment.librarymanagement.exception.BookAlreadyExist;
import co.uk.e2b.identitye2e.assessment.librarymanagement.exception.BookNotFound;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.AddItem;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.FindBy;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.FindListBy;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.RemoveItem;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.impl.AddBook;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.impl.FindByAuthor;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.impl.FindByISBN;
import co.uk.e2b.identitye2e.assessment.librarymanagement.service.book.impl.RemoveBook;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class Library {

  final private AddItem<Book> addBook;
  final private FindBy<Book> findByISBN;
  final private RemoveItem<Book> removeBook;
  final private FindListBy<Book> findByAuthor;
  List<Book> books;

  public Library() {
    this.books = new CopyOnWriteArrayList<>(new ArrayList<Book>());
    this.addBook = new AddBook();
    this.findByISBN = new FindByISBN();
    this.removeBook = new RemoveBook();
    this.findByAuthor = new FindByAuthor();
  }
  public ResultType addBook(Book book) {
    var found = findByISBN.find(books, book.getIsbn());
    if (found != null) {
      throw new BookAlreadyExist("Book already exists");
    }
    var newBook = Book.builder().isbn(book.getIsbn()).author(book.getAuthor())
        .title(book.getTitle()).publicationYear(book.getPublicationYear())
        .availableCopies(new AtomicInteger(1)).build();
    books = addBook.add(books, newBook);
    return ResultType.ADDED;
  }

  public ResultType removeBook(String isbn) {
    var found = findBookByISBN(isbn);
    books = removeBook.remove(books, found);
    return ResultType.REMOVED;
  }

  public Book findBookByISBN(String isbn) {
    var found = findByISBN.find(books, isbn);
    if (found == null) {
      throw new BookNotFound("Book not found with ISBN: " + isbn);
    }
    return found;
  }

  public List<Book> findBooksByAuthor(String author) {
    var found = findByAuthor.find(books, author);
    if (found == null) {
      throw new BookNotFound("Book not found with Author: " + author);
    }
    return found;
  }

  public ResultType borrowBook(String isbn) {
    var found = findBookByISBN(isbn);
    if (found == null) {
      throw new BookNotFound("Book not found with ISBN: " + isbn);
    }
    if(found.getAvailableCopies().intValue() == 0) {
      return ResultType.NO_AVAILABLE_BOOK;
    }
    found.getAvailableCopies().set(found.getAvailableCopies().decrementAndGet());
    return ResultType.DECREMENTED;
  }

  public ResultType returnBook(String isbn) {
    var found = findBookByISBN(isbn);
    if (found == null) {
      throw new BookNotFound("Book not found with ISBN: " + isbn);
    }
    found.getAvailableCopies().set(found.getAvailableCopies().incrementAndGet());
    return ResultType.INCREMENTED;
  }
}
