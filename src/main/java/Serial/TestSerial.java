package Serial;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;

public class TestSerial implements Serializable {
    static ArrayList<Data> list = new ArrayList<>();
    static String loc = "C:\\Users\\Dhinesh Kannan\\IdeaProjects\\project2\\src\\main\\resources\\e.txt";
    Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "123456");
    Statement stmt;

    public TestSerial() throws SQLException {
        this.stmt = this.conn.createStatement();
    }

    public Connection connect() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "123456");
        System.out.println("Connected to the PostgreSQL server successfully.");
        this.stmt = this.conn.createStatement();
        String sql = "CREATE TABLE Google(ID INT PRIMARY KEY     NOT NULL, NAME           TEXT    NOT NULL,  AGE            INT     NOT NULL,  ADDRESS        CHAR(50),  SALARY         REAL)";
        this.stmt.executeUpdate(sql);
        this.stmt.close();
        this.conn.close();
        return this.conn;
    }

    public void insert() throws SQLException {
        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/test", "postgres", "123456");
        ResultSet rs = this.stmt.executeQuery("SELECT * FROM Google;");

        while (rs.next()) {
            list.add(new Data(rs.getInt("age"), rs.getInt("id"), rs.getString("name"), rs.getFloat("salary"), rs.getString("address")));
        }

    }

    public void convertFile() throws Exception {
        FileOutputStream file = new FileOutputStream(loc);
        ObjectOutputStream out = new ObjectOutputStream(file);
        out.writeObject(list.get(0));
        out.flush();
        out.close();
        System.out.println("success");
    }

    public static void main(String[] args) throws Exception {
        TestSerial ser = new TestSerial();
        ser.connect();
        ser.insert();
        ser.convertFile();
    }
}




