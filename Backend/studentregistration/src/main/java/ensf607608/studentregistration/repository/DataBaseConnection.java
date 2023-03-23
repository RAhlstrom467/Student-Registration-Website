package ensf607608.studentregistration.repository;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class DataBaseConnection {

    @Autowired
    Environment env;

    public Connection getDatabaseConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(env.getProperty("db-url"), env.getProperty("db-username"),
                    env.getProperty("db-password"));
        } catch (Exception e) {
            System.out.println(e);
        }
        return con;
    }

}
