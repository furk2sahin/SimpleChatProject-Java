
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseConnection {
    private final String username = "root";
    
    private final String password = "";
    
    private final String databaseName = "emailDB";
    
    private final String host = "localhost";
    
    private final int port = 3306;
    
    private final String url = "jdbc:mysql://" + host + ":" +  port + "/" + databaseName + "?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    
    private Connection connection;

    public DatabaseConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // define driver
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database successfully.");
        } catch (SQLException ex) {
            System.out.println("Connection failed.");
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
}
