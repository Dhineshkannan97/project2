package Serial;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class TestSerial implements Serializable {
    static ArrayList<Data> list = new ArrayList<>();
    String s;
    String loc = "C:\\Users\\Dhinesh Kannan\\IdeaProjects\\project2\\src\\main\\resources\\z.txt";
    final String url = "jdbc:postgresql://localhost:5432/test";
    final String user = "postgres";
    final String password = "123456";
    Connection conn = DriverManager.getConnection(url, user, password);
    Statement stmt = conn.createStatement();

    public TestSerial(ArrayList<Data> list) throws SQLException {
    }

    public Connection connect() throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to the PostgreSQL server successfully.");
        stmt = conn.createStatement();
        String sql = "CREATE TABLE Google" +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " AGE            INT     NOT NULL, " +
                " ADDRESS        CHAR(50), " +
                " SALARY         REAL)";
        stmt.executeUpdate(sql);
        stmt.close();
        conn.close();
        return conn;
    }

    public void insert() throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
//            conn.setAutoCommit(false);
        ResultSet rs = stmt.executeQuery("SELECT * FROM Google;");
        while (rs.next()) {
            list.add(new Data(rs.getInt("age"), rs.getInt("id"), rs.getString("name"), rs.getFloat("salary"), rs.getString("address")));
        }
        for (Data data : list) {

            s = data.id + ":" + data.age + ":" + data.salary + ":" + data.name + ":" + data.address;
        }
        System.out.println(s);
    }

    public void convertFile() throws Exception {

        TestSerial ser = new TestSerial(list);
        FileOutputStream file = new FileOutputStream(loc);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(ser);
        out.flush();
        out.close();
        System.out.println("success");
    }

    public static void main(String[] args) throws Exception {
        TestSerial ser = new TestSerial(list);

        ser.connect();
        ser.insert();
        ser.convertFile();
    }

}




