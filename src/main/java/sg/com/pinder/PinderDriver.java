///**
// * 
// */
//package sg.com.pinder;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.UnknownHostException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.log4j.Logger;
//import org.apache.solr.client.solrj.SolrQuery;
//import org.apache.solr.common.SolrDocumentList;
//import org.bson.types.ObjectId;
//
//import com.google.gson.Gson;
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.Mongo;
//import com.mongodb.MongoException;
//import com.mongodb.gridfs.GridFS;
//import com.mongodb.gridfs.GridFSInputFile;
//
//import sg.com.pinder.database.MongoDatabase;
//import sg.com.pinder.database.MongoDatabaseBuilder;
//import sg.com.pinder.database.SolrMongoDatabase;
//import sg.com.pinder.exception.MongoDBException;
//import sg.com.pinder.exception.NoDatabaseException;
//import sg.com.pinder.pojo.EventRecord;
//import sg.com.pinder.pojo.Images;
//import sg.com.pinder.response.SolrResponse;
//import sg.com.pinder.util.FacesUtil;
//import sg.com.pinder.web.WebService;
//
///**
// * @author Han Liang Wee, Eric
// *
// */
//public class PinderDriver extends WebService
//{
//
//	static Logger logger = Logger.getLogger(PinderDriver.class);
//	
//	public PinderDriver() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//try {
//		//Mongo mongo = new Mongo("localhost", 27017);
////		DB db = mongo.getDB("admin");
////		DBCollection collection = db.getCollection("images");
////		
//		MongoDatabaseBuilder mdb = MongoDatabaseBuilder.getInstance(null, "database.xml");
//
//		MongoDatabase<Images> imgDB = mdb.build(Images.class);
//		
//		Path path = Paths.get("/home/Eric_Vader/Downloads/google.png");
//		byte[] data = Files.readAllBytes(path);
//		
//		Images testImg = new Images("png", "eric.aka.darth_vader@hotmail.com", "google.png", data);
//		
//		//=byte[] emp = {0,0,0,0};
//		testImg.setBytes(data);
//		testImg.setExtention("png");
//		imgDB.addToDB(testImg);
//		
//		logger.fatal(testImg.getId());
//		
//		imgDB.searchOneIdDB(testImg.getId());
//		
//		//		File imageFile = new File("/Users/ravenstorm/Documents/test.jpg");
////		GridFS gfsPhoto = new GridFS(db, "photo");
////		GridFSInputFile gfsFile = gfsPhoto.createFile(imageFile);
////		
////		Images image = new Images();
//		//image.setName(imageFile);
//		
////		gfsFile.save();
//} catch (UnknownHostException e) {
//	e.printStackTrace();
//} catch (MongoException e) {
//	e.printStackTrace();
//} catch (IOException e) {
//	e.printStackTrace();
//} catch (MongoDBException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//} catch (InstantiationException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//} catch (ClassNotFoundException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//} catch (NoDatabaseException e) {
//	// TODO Auto-generated catch block
//	e.printStackTrace();
//}
//
//		//authenticate mongodb and connect to mongodb
//	
////		SolrMongoDatabase<EventRecord, String> solrMongoDB = new SolrMongoDatabase<EventRecord, String>(mongo, "event", EventRecord.class);
////		
//////		SolrServer server = new HttpSolrServer("http://localhost:8983/solr/");
//////
//////		try{
//////			CoreAdminRequest adminRequest = new CoreAdminRequest();
//////			adminRequest.setAction(CoreAdminAction.STATUS);
//////			CoreAdminResponse adminResponse = adminRequest.process( server);
//////			NamedList<NamedList<Object>> coreStatus = adminResponse.getCoreStatus();
//////		} catch(Exception e) {
//////			e.printStackTrace();
//////		}
//////		
////		EventRecord event = new EventRecord();
////		event.setName(event.getId().toString());
////		event.setEventLocation("1.3667,103.750");
////		//event.setEventLocation("1.3667,103.750");
////		event.setDescription("hello");
////		
////		event.setStartDate(new Date());
////		event.setEndDate(new Date());
////		
////		event.setCost("0");
////		event.setUrl("http://google.com");
////		
////		event.setEventTextId(event.getId().toString());
////		event.setCategory("cat");
////
////////		
//////		solrMongoDB.addToDBandSolr(event);
////
//////	    try {
//////			server.add( docs );
//////			server.addBean(event);
//////			server.deleteByQuery("*:*");
//////			server.commit();
//////		} catch (SolrServerException | IOException e) {
//////			// TODO Auto-generated catch block
//////			e.printStackTrace();
//////		}
////		
////
////    	SolrMongoDatabase<EventRecord> solrMongoDB = PinderDriver.eventDB;
////		SolrQuery params = new SolrQuery();
//////		params.setQuery(this.eventTextId);
////		params.setQuery("eventTextId:"+"12345");
//////		params.setFields("eventTextId");
////    	logger.debug("Querying Solr:"+params.toString());
////
////		SolrResponse<EventRecord> docList = solrMongoDB.searchSolrByQuery(params);
////		
////		List<EventRecord> listOfEvents = docList.getDocuments();
////		
////		List<EventRecord> listDB = solrMongoDB.searchDB("12345", "eventTextId");
////		
////		System.out.println(new Gson().toJson(listOfEvents));
////		System.out.println(new Gson().toJson(listDB));
////		
////    	logger.fatal(new Gson().toJson(userDB.searchOneDB("eric.aka.darth_vader@hotmail.com", "email")));
////		
////    	System.out.println(new Gson().toJson(event));
////        event.setOwnerUserId(new ObjectId());
////        solrMongoDB.addToDBandSolr(event);
//    	
////		
////		solrMongoDB.deleteSolrByQuery(params);
//	}
//
//}
