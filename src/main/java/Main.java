import data.Book;
import ejb.EJB1;
import ejb.EJB2;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws NamingException {
        System.out.println("Hello!");

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
    }

}

