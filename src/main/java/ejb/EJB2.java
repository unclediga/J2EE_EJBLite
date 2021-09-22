package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import data.Book;

import java.util.List;

@Stateless
public class EJB2 {
    @PersistenceContext(unitName = "PU")
    private EntityManager em;

    public Book createBook(Book book) {
        return book;
    }

    public List<Book> findAll() {
        Query nativeQuery = em.createNativeQuery("SELECT id,name FROM Book");
        List resultList = nativeQuery.getResultList();
        return resultList;
    }

}

