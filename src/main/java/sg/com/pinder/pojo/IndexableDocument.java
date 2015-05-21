package sg.com.pinder.pojo;

import org.apache.solr.client.solrj.beans.Field;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.NotSaved;

/**
 * A document that can be indexed, generic to Events and UserProfile
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public abstract class IndexableDocument {

	@Id
	@Field
	protected ObjectId id;
	@NotSaved
	protected String type;
	
	/**
	 * creates an indexable document
	 */
	public IndexableDocument() {
		this.id=new ObjectId();
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
	
	
}
