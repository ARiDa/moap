package arida.ufc.br.moap.core.database.spi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import arida.ufc.br.moap.core.datasource.spi.IDataSourceFactory;

import org.apache.log4j.BasicConfigurator;
import org.openide.util.Lookup;

public class DatabaseService implements IDataSourceFactory {
	private static ConnectionProperty property;
	private static DatabaseService instance;
	private static final Logger log = Logger.getLogger(DatabaseService.class.getName());
	private DatabaseService(){}
	
	public static DatabaseService getInstance(){
		if(instance == null){
			instance = new DatabaseService();
			try {
//				System.out.println(PostgresqlBuilder.class.getName());
				property = ConnectionProperty.getInstance();
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
		}
		return instance;
	}
	
	
	@Override
	public AbstractDatabase getDataSource() {
		BasicConfigurator.configure();
		// TODO Auto-generated method stub
		System.out.println(1);
//		try {
//			Class c = Class.forName(property.getDriver());
//			System.out.println(Database.class.getClasses().length);
//			for(Class<?> c1 : Database.class.getClasses()){
//				Constructor cons = c1.getConstructor();
//				Database db = (Database)cons.newInstance();
//				System.out.println(db.getName());
//				if(db.getDriverClass().equals(property.getDriver())){
//					return db;
//				}
//			}
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		AbstractDatabase[] db = Lookup.getDefault().lookupAll(AbstractDatabase.class).toArray(new AbstractDatabase[0]);
		log.info("Loading database service");
		for(AbstractDatabase i : db){
			System.out.println(db.getClass().getName());
			if(i.getDriverClass().equals(property.getDriver())){
				System.out.println(i.getClass().getName());
				return i;
			}
		}
		log.severe("Database service has not been found");
		return null;
	}
	public static void main(String[] args){
		
		AbstractDatabase db = getInstance().getDataSource();
		System.out.println(db.getName());
		
	}

	

}
