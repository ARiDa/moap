package arida.ufc.br.moap.core.database.spi;

import arida.ufc.br.moap.core.datasource.spi.IDataSource;
import arida.ufc.br.moap.core.spi.IDataModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.openide.util.Exceptions;

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
            Exceptions.printStackTrace(ex);
        }
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
    public abstract IDataModel getInstanceModel(String query, IDataModel model);
    
}
