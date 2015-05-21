import static org.junit.Assert.*;

import java.net.UnknownHostException;

import org.junit.Test;

import com.google.gson.Gson;

import sg.com.pinder.database.MongoDatabase;
import sg.com.pinder.database.MongoDatabaseBuilder;
import sg.com.pinder.exception.MongoDBException;
import sg.com.pinder.exception.NoDatabaseException;
import sg.com.pinder.pojo.TestRecord;


public class MongoDatabaseTest {

	@Test
	public void test() {
		MongoDatabaseBuilder mdb;
		try {
			
			mdb = MongoDatabaseBuilder.getInstance("database.xml");
			
			MongoDatabase<TestRecord> testDB = mdb.build(TestRecord.class);
			
			TestRecord newRecord = new TestRecord();
			
			testDB.addToDB(newRecord);
			TestRecord retrieved = testDB.searchOneIdDB(newRecord.getId());
			
			Gson gson = new Gson();
			
			String first = gson.toJson(newRecord);
			String second = gson.toJson(retrieved); 
					
			assertTrue(first.equals(second));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}

}
