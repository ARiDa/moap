package arida.ufc.br.moap.db.postgres.imp;

import arida.ufc.br.moap.core.spi.Type;
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
 * InstancesBasedModelImpl is the model we use. This is a general model to work
 * with a Database.
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

			int databaseType[] = new int[rsmd.getColumnCount()];
			String columnName[] = new String[rsmd.getColumnCount()];

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				databaseType[i - 1] = rsmd.getColumnType(i);
				columnName[i - 1] = rsmd.getColumnName(i);
				// TODO Verify the default value...
				Type type = getType(databaseType[i - 1]);
				model.addAttribute(columnName[i - 1], type);
			}

			while (resultSet.next()) {
				Instance instance = new Instance(model);
				int columns = rsmd.getColumnCount();
				for (int i = 1; i <= columns; i++) {
					instance.setValue(i - 1,
							getValue(databaseType[i - 1], i, resultSet));
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
	 * @param type the SQL type
	 * 
	 * @return the name of the type
	 */

	public static Object getValue(int type, int idx, ResultSet rs)
			throws SQLException {
		switch (type) {

		case Types.BIGINT:
			return rs.getLong(idx);
			// case Types.BINARY:
			// return (Double) object;
			// case Types.BIT:
			// return "BIT";
			// case Types.CHAR:
			// return "CHAR";
			// case Types.DATE:
			// return (Date) object;

		case Types.DECIMAL:
			return rs.getDouble(idx);
		case Types.DOUBLE:
			return rs.getDouble(idx);
		case Types.FLOAT:
			return rs.getFloat(idx);
		case Types.INTEGER:
			return rs.getInt(idx);

			// case Types.LONGVARBINARY:
			// return "LONGVARBINARY";
			// case Types.LONGVARCHAR:
			// return "LONGVARCHAR";
			// case Types.NULL:
			// return null;
		case Types.NUMERIC:
			return rs.getDouble(idx);
			// case Types.OTHER:
			// return "OTHER";
			// case Types.REAL:
			// return "REAL";
			// case Types.SMALLINT:
			// return "SMALLINT";
			// case Types.TIME:
			// return "TIME";
			// case Types.TIMESTAMP:
			// return "TIMESTAMP";
			// case Types.TINYINT:
			// return "TINYINT";
			// case Types.VARBINARY:
			// return "VARBINARY";
		case Types.VARCHAR:
			return rs.getString(idx);
		default:
			return rs.getString(idx);

		}

	}

	public static Object getValue(int dbType, String idx, ResultSet rs)
			throws SQLException {
		switch (dbType) {

		case Types.BIGINT:
			return rs.getLong(idx);
			// case Types.BINARY:
			// return (Double) object;
			// case Types.BIT:
			// return "BIT";
			// case Types.CHAR:
			// return "CHAR";
			// case Types.DATE:
			// return (Date) object;

		case Types.DECIMAL:
			return rs.getDouble(idx);
		case Types.DOUBLE:
			return rs.getDouble(idx);
		case Types.FLOAT:
			return rs.getFloat(idx);
		case Types.INTEGER:
			return rs.getInt(idx);

			// case Types.LONGVARBINARY:
			// return "LONGVARBINARY";
			// case Types.LONGVARCHAR:
			// return "LONGVARCHAR";
			// case Types.NULL:
			// return null;
		case Types.NUMERIC:
			return rs.getDouble(idx);
			// case Types.OTHER:
			// return "OTHER";
			// case Types.REAL:
			// return "REAL";
			// case Types.SMALLINT:
			// return "SMALLINT";
			// case Types.TIME:
			// return "TIME";
			// case Types.TIMESTAMP:
			// return "TIMESTAMP";
			// case Types.TINYINT:
			// return "TINYINT";
			// case Types.VARBINARY:
			// return "VARBINARY";
		case Types.VARCHAR:
			return rs.getString(idx);
		default:
			return rs.getString(idx);

		}
	}

	public static Type getType(int type) {

		switch (type) {

		case Types.BIGINT:
			return Type.LONG;
			// case Types.BINARY:
			// return (Double) object;
			// case Types.BIT:
			// return "BIT";
			// case Types.CHAR:
			// return "CHAR";
			// case Types.DATE:
			// return (Date) object;

		case Types.DECIMAL:
			return Type.BIGDECIMAL;
		case Types.DOUBLE:
			return Type.DOUBLE;
		case Types.FLOAT:
			return Type.FLOAT;
		case Types.INTEGER:
			return Type.INT;

			// case Types.LONGVARBINARY:
			// return "LONGVARBINARY";
			// case Types.LONGVARCHAR:
			// return "LONGVARCHAR";
			// case Types.NULL:
			// return null;
		case Types.NUMERIC:
			return Type.DOUBLE;
			// case Types.OTHER:
			// return "OTHER";
			// case Types.REAL:
			// return "REAL";
			// case Types.SMALLINT:
			// return "SMALLINT";
			// case Types.TIME:
			// return "TIME";
			// case Types.TIMESTAMP:
			// return "TIMESTAMP";
			// case Types.TINYINT:
			// return "TINYINT";
			// case Types.VARBINARY:
			// return "VARBINARY";
		case Types.VARCHAR:
			return Type.STRING;
		default:
			return Type.STRING;

		}
	}
}
