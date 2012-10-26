package arida.ufc.br.core.test.database;

import static org.junit.Assert.*;

import mf.bd.postgresql.imp.PostgresqlProvider;
import mf.core.database.spi.Database;
import mf.core.database.spi.DatabaseService;

import org.apache.log4j.Logger;
import org.junit.Test;


public class ConnectionTest {

	@Test
	public void testGetDataSource() {
		Logger logger = Logger.getLogger("A");
		Database db = (Database) DatabaseService.getInstance().getDataSource();
		System.out.println(db.getName());
		
		
//		fail("Not yet implemented");
	}

}
