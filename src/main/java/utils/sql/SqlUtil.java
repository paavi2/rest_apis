package utils.sql;

import utils.Configs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlUtil {

    static Connection connection;
    private static void createSqlConnection(){
        String url = Configs.getProperty("public.rfam.jdbc.url");
        String username = Configs.getProperty("public.rfam.jdbc.username");
        String pwd = Configs.getProperty("public.rfam.jdbc.password");
        
        // For public databases that don't require password, use empty string
        if(pwd == null) {
            pwd = "";
        }
        
        try {
            if(connection == null || connection.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                DriverManager.setLoginTimeout(10);
                System.out.println("Attempting to connect to: " + url + " with username: " + username);
                connection = DriverManager.getConnection(url, username, pwd);
                System.out.println("Database connection established successfully");
            }
        } catch (SQLException e) {
            System.err.println("SQL Connection failed: " + e.getMessage());
            throw new RuntimeException("Failed to connect to database: " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL Driver not found: " + e.getMessage());
            throw new RuntimeException("MySQL Driver not found: " + e.getMessage(), e);
        }
    }

    //Utility for running select query
    public static Object[][] executeQuery(String query){
        List<Object[]> rows = new ArrayList<>();
        createSqlConnection();
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(query)) {
             
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columns = resultSetMetaData.getColumnCount();
            while(resultSet.next()){
                Object[] row = new Object[columns];
                for(int i=1; i<= columns; i++){
                    row[i-1] = resultSet.getObject(i);
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Failed to execute query: " + query + ". Error: " + e.getMessage());
            throw new RuntimeException("Failed to execute query: " + e.getMessage(), e);
        }
        return rows.toArray(new Object[0][0]);
    }

    //Util method for data manipulation (insert/update/delete)
    public static int executeUpdate(String query){
        createSqlConnection();
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("Failed to execute update: " + query + ". Error: " + e.getMessage());
            throw new RuntimeException("Failed to execute update: " + e.getMessage(), e);
        }
    }
    
    //Method to close database connection
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed successfully");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    //Method to test database connectivity
    public static boolean testConnection() {
        try {
            createSqlConnection();
            return connection != null && !connection.isClosed();
        } catch (Exception e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }

}
