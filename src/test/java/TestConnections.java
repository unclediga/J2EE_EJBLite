import data.Book;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class TestConnections {
    private static final String URL_derby = "jdbc:derby://localhost:1527/examples;databaseName=examples";
    private static final String USER_derby = "examples";
    private static final String PASSWORD_derby = "examples";

    private static final String URL_H2 = "jdbc:h2:~/test";
    private static final String USER_H2 = "sa";
    private static final String PASSWORD_H2 = "";
    private boolean exFlag;

    @Test
    public void test_h2() {
        Connection connection = null;
        exFlag = false;
        try {
            DriverManager.registerDriver(new org.h2.Driver());
            connection = DriverManager.getConnection(URL_H2, USER_H2, PASSWORD_H2);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id,name FROM BOOK WHERE id <= 3 ");
            Book booksReal[] = new Book[]{null, null, null};
            int i = 0;
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt(1));
                book.setName(resultSet.getString(2));
                booksReal[i++] = book;
            }
            resultSet.close();
            statement.close();
            connection.close();
            Assert.assertArrayEquals(TestBooks.books, booksReal);
        } catch (SQLException e) {
            exFlag = true;
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        Assert.assertEquals(false, exFlag);
    }

    @Test
    public void test_derby() {
        Connection connection = null;
        exFlag = false;
        try {
            DriverManager.registerDriver(new org.apache.derby.jdbc.ClientDriver());
            connection = DriverManager.getConnection(URL_derby, USER_derby, PASSWORD_derby);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id,name FROM BOOK WHERE id <= 3 ");
            Book booksReal[] = new Book[]{null, null, null};
            int i = 0;
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getInt(1));
                book.setName(resultSet.getString(2));
                booksReal[i++] = book;
            }
            resultSet.close();
            statement.close();
            connection.close();
            Assert.assertArrayEquals(TestBooks.books, booksReal);
        } catch (SQLException e) {
            exFlag = true;
            e.printStackTrace();
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        Assert.assertEquals(false, exFlag);
    }


}
