import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    Connection con;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:localhost://localhost:3306/test";

        Class.forName("com.mysql.jdbc.Driver");

        con = DriverManager.getConnection(connectionString,
                "root", "root");
        return con;
    }
}