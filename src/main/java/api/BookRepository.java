package api;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.name like ?1%")
    List<Book> findByNameAndSort(String name , Sort sort);

    @Query("SELECT b FROM Book b WHERE b.author like ?1%")
    List<Book> findByAuthorAndSort(String author, Sort sort);

    @Query("SELECT b FROM Book b WHERE b.yearOfPublishing = ?1")
    List<Book> findByYearOfPublishingAndSort(String yearOfPublishing, Sort sort);

    @Query("SELECT b FROM Book b WHERE b.isbnCode = ?1")
    List<Book> findByISBNCodeAndSort(String author, Sort sort);
}
