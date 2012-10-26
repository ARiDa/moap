package arida.ufc.br.moap.db.postgres.types;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TypeConverter {
	
	private static TypeConverter instance;
	private Properties prop;
	
	private TypeConverter(){
		instance = new TypeConverter();
	}
	
	public static TypeConverter getInstance(){
		if(instance == null){
			instance = new TypeConverter();
		}
		return instance;
	}
	
	@SuppressWarnings("unused")
	private void setProperties(){
		prop = new Properties();
		try {
			prop.load(new FileInputStream("postgresql.properties"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getType(String type){
		return prop.getProperty(type);
	}
	
	public String getType(String type,boolean defaultValue){
		if(defaultValue == true)
			return prop.getProperty(type);
		return getType(type);
	}
	
	
	
}
