package ejb;

import data.Book;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.transaction.*;
import java.util.List;

@Stateless
public class EJB2 {
    @PersistenceContext(unitName = "PU",type = PersistenceContextType.TRANSACTION)
    private EntityManager em;

    @Resource
    UserTransaction utx;

    public Book createBook(String name) {
        int id = getMaxId() + 1;
        Book book = new Book();
        book.setId(id);
        book.setName(name + "[" + id + "]");
        em.persist(book);
        em.flush();
//        em.getTransaction().begin();
//        try {
//            utx.begin();
//            em.persist(book);
//            em.flush();
//            utx.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//            try {
//                utx.rollback();
//            } catch (SystemException ex) {
//                ex.printStackTrace();
//            }
//        }
//        em.getTransaction().commit();
        return book;
    }

    public int getMaxId() {
        List list = em.createNativeQuery("SELECT MAX(id) FROM Book").getResultList();
        return (int) list.get(0);
    }

    public List<Book> findAll() {
        Query query = em.createQuery("SELECT o FROM Book o");
        return query.getResultList();
    }

}

