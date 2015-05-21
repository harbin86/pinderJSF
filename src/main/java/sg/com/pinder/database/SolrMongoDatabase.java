/**
 * Initializes a database where there are 2 databases, one to solr the other to mongodb
 */
package sg.com.pinder.database;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.mongodb.morphia.Datastore;

import sg.com.pinder.response.SolrResponse;

/**
 * @author Han Liang Wee, Eric
 * @param <T> The type of the data stored in the database
 */
public class SolrMongoDatabase<T> extends MongoDatabase<T> {

	private static Logger logger = Logger.getLogger(SolrMongoDatabase.class);
	
	private SolrServer solrServer;
	
	/**
	 * Initializes a database object that stores to both Solr and MongoDB 
	 * @param ds
	 * @param inObjectClass
	 * @param solrServer
	 */
	protected SolrMongoDatabase(Datastore ds, Class<T> inObjectClass, SolrServer solrServer) {
		super(ds, inObjectClass);
		this.solrServer = solrServer;
	}
	
	/**
	 * stores to both mongoDB and SolrServer
	 * @param inRecord the record to be stored
	 */
	public void addToDBandSolr(T inRecord)
	{
		super.addToDB(inRecord);
		
		try {
			solrServer.addBean(inRecord);
			solrServer.commit();
		} catch (IOException | SolrServerException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
		
	/**
	 * Searches the Solr Server using a Solr Query Object
	 * @param params
	 * @return List of the objects stored in this database
	 */
	public SolrResponse<T> searchSolrByQuery(SolrQuery params)
	{
		try{
		    QueryResponse response = solrServer.query(params);
		    if(response.getStatus()==0)
		    	return new SolrResponse<T>(response.getResults(), objectClass);
		} catch(SolrServerException sse) {
			logger.debug(sse.getMessage());
			sse.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Deletes the documents returned by the Solr Server using the query
	 * @param params query for the objects to perform the delete
	 */
	public void deleteSolrByQuery(SolrQuery params)
	{
		try{
		    UpdateResponse response = solrServer.deleteByQuery(params.getQuery());
		    if(response.getStatus()!=0)
		    	logger.error("No such data to delete for query:"+params.getQuery());
		} catch (SolrServerException | IOException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes the document by the specified Indexes
	 * @param indexList indexes to be deleted
	 */
	public void deleteSolrByIndex(List<String> indexList) {
		try {
			solrServer.deleteById(indexList);
		} catch (SolrServerException | IOException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
	
}
