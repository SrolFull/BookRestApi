package api.service.impl;

import api.Book;
import api.BookRepository;
import api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    EntityManager em;
    @Autowired
    BookRepository bookRepository;

    @Override
    public ResponseEntity<Object> deleteBook (Long id){
        try {
            bookRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @Override
    public List<Book> getAllBooks (){
        return bookRepository.findAll();
    }

    @Override
    public ResponseEntity<Object> addBook (Book book){
        bookRepository.saveAndFlush(book);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Object> updateBook (Book newBook, Long id){
        return bookRepository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.setAuthor(newBook.getAuthor());
                    book.setIsbnCode(newBook.getIsbnCode());
                    book.setYearOfPublishing(newBook.getYearOfPublishing());
                    bookRepository.saveAndFlush(book);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    @Override
    public List<Book> findBook (String searchField, String searchParam, String sortField, String sortDir) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);

        criteriaQuery.select(bookRoot)
                .where(criteriaBuilder.equal(bookRoot.get(searchField), searchParam));

        if (sortDir.equals("asc"))
            criteriaQuery.orderBy(criteriaBuilder.asc(bookRoot.get(sortField)));

        if (sortDir.equals("desc"))
            criteriaQuery.orderBy(criteriaBuilder.desc(bookRoot.get(sortField)));

        return em.createQuery(criteriaQuery).getResultList();
    }
}
