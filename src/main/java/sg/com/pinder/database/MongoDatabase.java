/**
 * MongoDatabase class to handle mongoDB queries and storage
 */

package sg.com.pinder.database;

import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import sg.com.pinder.exception.RandomFieldNotInitializedException;

/**
 * @author Han Liang Wee, Eric
 * @param <T> The type of the data stored in the database
 * @version 27/May/2014
 */
public class MongoDatabase <T>
{
	private Datastore ds;
	protected Class<T> objectClass;

	/**
	 * Constructs a MongoDatabase object for a particular object class
	 * @param ds datastore from morphia to back the read and writing
	 * @param objectClass the object class to work with in this database
	 */
	protected MongoDatabase(Datastore ds, Class<T> objectClass)
	{
		this.objectClass = objectClass;
		this.ds = ds;
	}
	
	/**
	 * A method to check and add to Database if the object does not exist in the database
	 * @param inKey	The key to be used to search the database
	 * @param field The field name of the data in MongoDB
	 * @param inRecord	The record to be saved to the database.
	 */
	public <E> void checkAndAddToDB(E inKey, String field, T inRecord)
	{
		if(this.searchOneDB(inKey, field)==null)
		{
			this.addToDB(inRecord);
		}
	}
	
	/**
	 * Adds a type T object to the database.
	 * Does not check the validity of the incoming record
	 * @param inRecord	The record to be saved to the database.
	 */
	public void addToDB(T inRecord)
	{
		ds.save(inRecord);
	}
	
	/**
	 * Searches the database for the record with the given key
	 * Does not check for validity of the field.
	 * @param inKey	The key to be used to search the database
	 * @param field The field name of the data in MongoDB
	 * @return	The type T object of that key, 
	 * <code>null</code> if does not exist.
	 */
	public <E> T searchOneDB(E inKey, String field)
	{
		T retrieveRecord=null;
		retrieveRecord = ds.find(objectClass).field(field).equal(inKey).get();
		return retrieveRecord;

	}
	
	/**
	 * Searches the database for the record with the given key
	 * @param inKey	The key to be used to search the database
	 * @param field The field name of the data in MongoDB
	 * @return	The type T object of that key, 
	 * <code>null</code> if does not exist.
	 */
	public <E> List<T> searchDB(E inKey, String field)
	{
		List<T> retrieveRecord=null;
		retrieveRecord = ds.find(objectClass).field(field).equal(inKey).asList();
		return retrieveRecord;

	}
	
	/**
	 * Get query
	 * @return
	 */
	public Query<T> getQuery() {
		return this.ds.find(objectClass);
	}
	
	/**
	 * Searches the database for the record with the given key
	 * Does not check for validity of the postalcode.
	 * @param id The Id to be used to to search the database
	 * @return The type T object of that key,
	 * <code>null</code> if does not exist.
	 */
	public <E> T searchOneIdDB(E id)
	{
		return searchOneDB(id, "_id");
	}

	/**
	 * Retrieve a list of type T object with just one specified field
	 * @param fieldName the parameter to be returned
	 * @return The list of type T objects
	 */
	public List<T> getAllAdv(String fieldName)
	{
		return ds.find(objectClass).retrievedFields(true, fieldName).asList();
	}
	
	/**
	 * Retrieve a list of all type T object stored
	 * @return The list of type T objects
	 */
	public List<T> getAll()
	{
		return ds.find(objectClass).asList();
	}
	
	/**
	 * Retrieve size of the database
	 * @return The number of records 
	 */
	public long getSize()
	{
		return ds.getCount(objectClass);
	}
	
	/**
	 * Randomly retrieves a record
	 * @return the randomly selected record
	 * @throws RandomFieldNotInitializedException if no random field exists
	 */
	public T getRandom() throws RandomFieldNotInitializedException
	{
		try{
			objectClass.getDeclaredField("random");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RandomFieldNotInitializedException("Random field not initialized.");
		}
		
		double randomNumber = Math.random();
		T returnThis = ds.createQuery(objectClass).field("random").lessThan(randomNumber).get();
		if(returnThis==null)
			returnThis = ds.createQuery(objectClass).field("random").greaterThan(randomNumber).get();
		
		return returnThis;
	}
	
	/**
	 * Deletes an object from the collection
	 * @param toBeDeleted The object to be deleted
	 */
	public void delete(T toBeDeleted)
	{
		ds.delete(toBeDeleted);
	}
	
}
