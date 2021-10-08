package api.service;

import api.Book;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {
    ResponseEntity<Object> addBook (Book book) ;

    ResponseEntity<Object> updateBook (Book book, Long id) ;

    ResponseEntity<Object> deleteBook (Long id) ;

    List<Book> getAllBooks () ;

    List<Book> findBook (String searchField, String searchParam, String sortField, String sortDir) ;
}
