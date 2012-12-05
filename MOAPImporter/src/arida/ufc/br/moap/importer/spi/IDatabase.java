
package arida.ufc.br.moap.importer.spi;

import java.io.Serializable;

/**
 ** Database description and connection details.
 * 
 * @author franzejr
 */
public interface IDatabase extends Serializable {
    
    public String getName();

    public SQLDriver getSQLDriver();

    public String getHost();

    public int getPort();

    public String getUsername();

    public String getPasswd();

    public String getDBName();

    public void setName(String name);

    public void setSQLDriver(SQLDriver driver);

    public void setHost(String host);

    public void setPort(int port);

    public void setUsername(String username);

    public void setPasswd(String passwd);

    public void setDBName(String dbName);

    
}
