package arida.ufc.br.moap.db.postgres.types;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PostgisProperties {
	private static PostgisProperties instance;
	private Properties prop;
	
	private PostgisProperties(){
		instance = new PostgisProperties();
	}
	
	public static PostgisProperties getInstance(){
		if(instance == null){
			instance = new PostgisProperties();
		}
		return instance;
	}
	
	@SuppressWarnings("unused")
	private void setProperties(){
		prop = new Properties();
		try {
			prop.load(new FileInputStream("postgis.properties"));
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
	
}
