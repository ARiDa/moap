package arida.ufc.br.moap.core.database.spi;

import arida.ufc.br.moap.core.datasource.spi.IDataSource;
import arida.ufc.br.moap.core.spi.IDataModel;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author franzejr
 */
public abstract class AbstractDatabase implements IDataSource {

    private static Logger logger = Logger.getLogger(AbstractDatabase.class);
    protected static ConnectionProperty p;
    protected static Connection connection;
    protected static AbstractDatabase instance;
    protected String user;
    protected String password;
    protected String url;

    public AbstractDatabase(String user, String password, String url){
        this.user = user;
        this.password = password;
        this.url = url;
        
        try {
            connection  = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public AbstractDatabase(){
    	p = null;
		try {
			p = ConnectionProperty.getInstance();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection = p.getConnection();
		logger.info(ConnectionProperty.getString());
    }
    
    /**
     *
     * Get an instance from a connection
     * @return Instance
     */
    public abstract AbstractDatabase getInstance();
	
    public String toString() {
        return ConnectionProperty.getString();
    }

    public abstract String getDriverClass();
    /*
     * 
     * //TODO
     * Get the model detection automatically
     */
    public abstract IDataModel retrieveInstances(String query, IDataModel model);
    
}
