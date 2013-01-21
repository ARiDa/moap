package arida.ufc.br.moap.db.postgres.imp;

import arida.ufc.br.moap.core.spi.IDataModel;
import arida.ufc.br.moap.datamodelapi.imp.MapModelImpl;
import arida.ufc.br.moap.importer.spi.ITranslater;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.openide.util.Exceptions;

/**
 *
 * GeneralTranslater Translate a query for a specific model
 *
 * @author franzejr
 * @author igobrilhante
 */
public class GeneralTranslater implements ITranslater {

    Connection connection;
    ResultSet resultSet;

    public GeneralTranslater(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void translate(String query, IDataModel model) {
        model = new MapModelImpl<Object, List<Object>>();
        try {
            DatabaseMetaData mtdt = connection.getMetaData();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            ResultSetMetaData rsmd = resultSet.getMetaData();

            int databaseType[] = new int[rsmd.getColumnCount()];
            String columnName[] = new String[rsmd.getColumnCount()];

            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                databaseType[i] = rsmd.getColumnType(i);
                columnName[i] = rsmd.getColumnName(i);
            }

            MapModelImpl<Object, List<Object>> newModel = new MapModelImpl<Object, List<Object>>();
            HashMap<Object, List<Object>> hashMap = new HashMap<Object, List<Object>>();

            while (resultSet.next()) {

                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    hashMap.put(columnName[i], new ArrayList<Object>());
                }

                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    Object object = typeName(databaseType[i], resultSet.getObject(i));
                    hashMap.get(columnName[i]).add(object);
                }
            }
            //Fill out the model
            model = (IDataModel) hashMap;

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
    public static Object typeName(int type, Object object) {

        switch (type) {
            case Types.BIGINT:
                return (Integer) object;
            case Types.BINARY:
                return (Double) object;
            case Types.BIT:
                return "BIT";
            case Types.CHAR:
                return "CHAR";
            case Types.DATE:
                return (Date) object;
            case Types.DECIMAL:
                return "DECIMAL";
            case Types.DOUBLE:
                return (Double) object;
            case Types.FLOAT:
                return (Float) object;
            case Types.INTEGER:
                return (Integer) object;
            case Types.LONGVARBINARY:
                return "LONGVARBINARY";
            case Types.LONGVARCHAR:
                return "LONGVARCHAR";
            case Types.NULL:
                return null;
            case Types.NUMERIC:
                return "NUMERIC";
            case Types.OTHER:
                return "OTHER";
            case Types.REAL:
                return "REAL";
            case Types.SMALLINT:
                return "SMALLINT";
            case Types.TIME:
                return "TIME";
            case Types.TIMESTAMP:
                return "TIMESTAMP";
            case Types.TINYINT:
                return "TINYINT";
            case Types.VARBINARY:
                return "VARBINARY";
            case Types.VARCHAR:
                return (String) object;
            default:
                return null;
        }
    }
}
