package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
        private Connection connection = null;
    public Connection setConnection() {
       try {
            String url = "jdbc:mysql://localhost:3306/web_tuning";
            String user = "root";
            String pass = "";
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
        }
        return connection;
    }
}
