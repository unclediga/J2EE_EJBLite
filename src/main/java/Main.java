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
    private static final String URL_H2 = "jdbc:h2:~/test";

    public static void main(String[] args) throws NamingException {
        System.out.println("Hello!");
        System.out.println("Connection test...");
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL_H2, "sa", "");
            DriverManager.registerDriver(new org.h2.Driver());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM BOOK");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println("row " + resultSet.getRow() + " id:" + id + " name:" + name);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }


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

