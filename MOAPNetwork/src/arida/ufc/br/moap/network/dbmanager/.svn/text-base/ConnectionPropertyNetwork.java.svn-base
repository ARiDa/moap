/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.network.dbmanager;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author igobrilhante
 */
public class ConnectionPropertyNetwork {

    private static ConnectionPropertyNetwork instance;
    private static Connection connection;
    private static Properties db_properties;
    private static String driver;
    private static String url;
    private static String username;
    private static String password;
    private static Logger logger = Logger.getLogger(ConnectionPropertyNetwork.class);

    private ConnectionPropertyNetwork() {
    }

    public static ConnectionPropertyNetwork getInstance() throws IOException, SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new ConnectionPropertyNetwork();
            db_properties = new Properties();
            db_properties.load(new FileInputStream("database.properties"));
            driver = db_properties.getProperty("jdbc.driver");
            url = db_properties.getProperty("jdbc.url");
            username = db_properties.getProperty("jdbc.username");
            password = db_properties.getProperty("jdbc.password");
            Class.forName(driver);
            try {
                connection = DriverManager.getConnection(url,
                        username,
                        password);
            } catch (SQLException e) {
                logger.error(String.format("Driver: %s\nURL: %s\nUSERNAME: %s\n", driver, url, username));
                throw new SQLException("Connection to database has been failed:" + getString());
            }
        }
        return instance;

    }

    public String getDriver() {
        return driver;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public void close() throws Exception {
        instance.close();
    }

    public Connection getConnection() {
        return connection;
    }

    public static String getString() {
        return driver + " " + url + " " + username;
    }
}
