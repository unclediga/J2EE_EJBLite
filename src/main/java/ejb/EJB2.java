package ejb;

import data.Book;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import java.util.List;

@Stateless
//@DataSourceDefinition(name = "java:app/examplesDS", className = "org.apache.derby.jdbc.ClientDataSource", portNumber = 1527, serverName = "localhost", databaseName = "examples", user = "examples", password = "examples")
//@DataSourceDefinition(name = "java:app/examplesDS", className = "org.h2.Driver",url = "jdbc:h2:C:/Users/BorodinovI/test", user = "sa", password = "")

public class EJB2 {
    @PersistenceContext(unitName = "PU")
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

