/**
 * Solr Response Class to represent the data returned by a Solr Server
 */
package sg.com.pinder.response;

import java.util.ArrayList;
import java.util.List;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.bson.types.ObjectId;

import sg.com.pinder.gson.ObjectIdTypeAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 * @param <T> Document that is retrieved from the server
 */
public class SolrResponse <T> extends SolrDocumentList {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static private Gson gson;
	
	static{
		GsonBuilder gb = new GsonBuilder();
		gb.registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter.ObjectIdDeserializer());
		gb.registerTypeAdapter(ObjectId.class, new ObjectIdTypeAdapter.ObjectIdSerializer());
//		gb.registerTypeAdapter(Date.class, new ObjectIdTypeAdapter.DateDeserializer());
//		gb.registerTypeAdapter(Date.class, new ObjectIdTypeAdapter.DateSerializer());

//gb.setPrettyPrinting().
		gson = gb.create();
	}
	
	private List<T> documents;
	
	/**
	 * Solr response, considering conversion of the documents to proper objects
	 * @param documentList documents returned from query
	 * @param objectClass the object Class in Question
	 */
	public SolrResponse(SolrDocumentList documentList, Class<T> objectClass) {
		
		if(documentList.size()>0) {
		    this.documents = new ArrayList<T>();
		    for(SolrDocument eaDocument:documentList){
		    	this.documents.add(gson.fromJson(gson.toJson(eaDocument), objectClass));
		    }
		} else {
			this.documents = null;
		}
		
	}
	
	/**
	 * @return the documents
	 */
	public List<T> getDocuments() {
		return documents;
	}
	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<T> documents) {
		this.documents = documents;
	}
	
}
