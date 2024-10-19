package co.uk.e2b.identitye2e.assessment.librarymanagement.service;

import java.util.List;

public interface RemoveItem<T> {

  List<T> remove(List<T> currentItems, T itemToDelete);
}

