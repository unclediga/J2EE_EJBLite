package ejb;

import data.Book;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EJB2Test {

    static EJBContainer container = null;

    @BeforeClass
    public static void startUp() {
        System.out.println("startUp");
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File("target/test-classes"));
        container = EJBContainer.createEJBContainer(properties);
        System.out.println("startUp Container = " + container);
    }

    @AfterClass
    public static void shutDown() {
        System.out.println("shutDown Container = " + container);
        if (container != null) {
            container.close();
        }
    }

    @Test
    public void testFindAll() {

        try {
            Context context = container.getContext();

            EJB1 ejb1 = (EJB1) context.lookup("java:global/classes/EJB1");
            System.out.println(ejb1.getHello());

            EJB2 ejb2 = (EJB2) context.lookup("java:global/classes/EJB2");
            for (Book b : ejb2.findAll()) {
                System.out.println(b);
            }
            List<Book> all = ejb2.findAll();
            Assert.assertEquals(3, all.size());

            System.out.println("Creating new book...");
            Book myNewBook = ejb2.createBook("My new book");

            System.out.println("Was created: " + myNewBook);

            for (Book b : ejb2.findAll()) {
                System.out.println(b);
            }

        } catch (Exception e) {
            System.err.println("My:Error create WL!");
            e.printStackTrace();
        }
        System.out.println("Bye, tests!");
    }
}