/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arida.ufc.br.moap.importer.spi;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author franzejr
 */
public interface SQLDriver extends Serializable {
    
    public String getPrefix();

    public Connection getConnection(String connectionUrl, String username, String passwd) throws SQLException;

    @Override
    public String toString();
    
}
