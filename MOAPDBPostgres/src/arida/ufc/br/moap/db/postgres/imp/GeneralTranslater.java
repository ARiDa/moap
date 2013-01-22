package arida.ufc.br.moap.db.postgres.imp;

import arida.ufc.br.moap.datamodelapi.instances.api.AttributeType;
import arida.ufc.br.moap.datamodelapi.instances.imp.Instance;
import arida.ufc.br.moap.datamodelapi.instances.imp.InstancesBasedModelImpl;
import arida.ufc.br.moap.importer.spi.ITranslater;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import org.openide.util.Exceptions;

/**
 *
 * GeneralTranslater Translate a query for a specific model
 * InstancesBasedModelImpl is the model we use. This is a general
 * model to work with a Database.
 *
 * @author franzejr
 * @author igobrilhante
 */
public class GeneralTranslater implements ITranslater<InstancesBasedModelImpl> {
        
    Connection connection;
    ResultSet resultSet;

    public GeneralTranslater(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void translate(String query, InstancesBasedModelImpl model) {
        
        try {
            
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ResultSetMetaData rsmd = resultSet.getMetaData();

            int databaseType[] = new int[rsmd.getColumnCount()+1];
            String columnName[] = new String[rsmd.getColumnCount()+1];
            
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                databaseType[i] = rsmd.getColumnType(i);
                columnName[i] = rsmd.getColumnName(i);
                //TODO Verify the default value...
                model.addAttribute(columnName[i], getAttributeType(databaseType[i]) );
            }
            
            

            while (resultSet.next()) {
                Instance instance = new Instance(model);
                
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    instance.setValue(i-1, resultSet.getObject(i));
                }
                model.addInstance(instance);
            }
            
        } catch (SQLException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    /*   
     * Returns the name associated with a SQL type.
     *
     * @param type 	the SQL type
     * @return the name of the type
     */
    public static AttributeType getAttributeType(int type) {

        switch (type) {
            
//            case Types.BIGINT:
//                return (Integer) object;
//            case Types.BINARY:
//                return (Double) object;
//            case Types.BIT:
//                return "BIT";
//            case Types.CHAR:
//                return "CHAR";
//            case Types.DATE:
//                return (Date) object;
//            case Types.DECIMAL:
//                return "DECIMAL";
            case Types.DOUBLE:
                return AttributeType.DOUBLE;
            case Types.FLOAT:
                return AttributeType.FLOAT;
            case Types.INTEGER:
                return AttributeType.INT;
//            case Types.LONGVARBINARY:
//                return "LONGVARBINARY";
//            case Types.LONGVARCHAR:
//                return "LONGVARCHAR";
//            case Types.NULL:
//                return null;
            case Types.NUMERIC:
                return AttributeType.DOUBLE;
//            case Types.OTHER:
//                return "OTHER";
//            case Types.REAL:
//                return "REAL";
//            case Types.SMALLINT:
//                return "SMALLINT";
//            case Types.TIME:
//                return "TIME";
//            case Types.TIMESTAMP:
//                return "TIMESTAMP";
//            case Types.TINYINT:
//                return "TINYINT";
//            case Types.VARBINARY:
//                return "VARBINARY";
            case Types.VARCHAR:
                return AttributeType.STRING;
            default:
                return AttributeType.STRING;
            
        }
    }
}
