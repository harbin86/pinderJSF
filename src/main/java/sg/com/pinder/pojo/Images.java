package sg.com.pinder.pojo;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;
import org.mongodb.morphia.annotations.Property;

/**
 * A document that can be indexed, generic to Events and UserProfile
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
@Entity(value = "image", noClassnameStored = true)
@Indexes(@Index(value = "email, fileName, ext", unique=true, dropDups=true))
public class Images {

	@Id
	protected ObjectId id;
	@Property("ext")
	protected String extention;
	@Property("email")
	protected String email;
	@Property("fileName")
	protected String fileName;
	protected byte[] bytes;
	
	/**
	 * Default
	 */
	public Images() {
	}
	
	/**
	 * creates an indexable document
	 */
	public Images( String extension, String email, String fileName, byte[] bytes) {
		this.id=new ObjectId();
		this.extention = extension;
		this.email = email;
		this.fileName = fileName;
		this.bytes = bytes;
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

	public String getExtention() {
		return extention;
	}

	public void setExtention(String extention) {
		this.extention = extention;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

}
