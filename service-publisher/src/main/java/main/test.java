package main;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class test {
    public static void main(String[] args){
        Properties properties = new Properties();
        InputStream inputStream = null;
        Connection conn = null;
        try{
            inputStream = test.class.getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);
            Class.forName(properties.getProperty("jdbc.driver"));
            conn = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
            System.err.println("ok,"+conn.getCatalog());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null)
                    inputStream.close();
                if(conn != null) conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
