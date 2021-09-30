package ejb;

import data.Book;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.Context;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EJB2Test extends TestCase {
    @Inject
    EJB2 ejb2;

    @BeforeClass
    public void startup(){

    }
    @Test
    public void testFindAll() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File("target/classes"));

        EJBContainer container = null;
        try {
            container = EJBContainer.createEJBContainer(properties);
            Context context = container.getContext();

            EJB1 ejb1 = (EJB1) context.lookup("java:global/classes/EJB1");
            System.out.println(ejb1.getHello());

            EJB2 ejb2 = (EJB2) context.lookup("java:global/classes/EJB2");
            for(Book b: ejb2.findAll()){
                System.out.println(b);
            }

            System.out.println("Creating new book...");
            Book myNewBook = ejb2.createBook("My new book");
            System.out.println("Was created: " + myNewBook);

            for(Book b: ejb2.findAll()){
                System.out.println(b);
            }

        } catch (Exception e) {
            System.err.println("My:Error create WL!");
            e.printStackTrace();
        } finally {
            if (container != null) {
                container.close();
            }
        }
        System.out.println("Bye!");
        System.exit(0);
        List<Book> all = ejb2.findAll();
        Assert.assertEquals(3, all.size());
    }
}