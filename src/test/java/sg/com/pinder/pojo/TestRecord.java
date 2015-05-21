/**
 * Class to store all User data and settings
 */
package sg.com.pinder.pojo;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
@Entity(value = "test", noClassnameStored = true)
public class TestRecord implements Serializable {
	
	@Id
	ObjectId id;
	Date currentDate;
	
	public TestRecord() {
		this.currentDate = new Date();
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the currentDate
	 */
	public Date getCurrentDate() {
		return currentDate;
	}

	/**
	 * @param currentDate the currentDate to set
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	
	
}