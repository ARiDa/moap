/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.core.database.spi;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author franzejr
 */
public interface SQLDriver {
    
    public String getPrefix();

    public Connection getConnection(String connectionUrl, String username, String passwd) throws SQLException;

    @Override
    public String toString();
    
}
