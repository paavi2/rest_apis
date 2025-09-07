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
        String pwd = "";
        try {
            if(connection == null || connection.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                DriverManager.setLoginTimeout(5);
                connection = DriverManager.getConnection(url,username,pwd);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //Utility for running select query
    public static Object[][] executeQuery(String query){
        List<Object[]> rows = new ArrayList<>();
        createSqlConnection();
        try {
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
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
            throw new RuntimeException(e);
        }
        return rows.toArray(new Object[0][0]);

    }

    //Util method for data manipulation (insert/update/delete)
    public static int executeUpdate(String query){
        createSqlConnection();
        try {
            return connection.createStatement().executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
