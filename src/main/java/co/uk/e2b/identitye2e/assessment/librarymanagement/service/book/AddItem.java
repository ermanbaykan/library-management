package co.uk.e2b.identitye2e.assessment.librarymanagement.service.book;

import java.util.List;

public interface AddItem<T> {

  List<T> add(List<T> list, T item);
}
