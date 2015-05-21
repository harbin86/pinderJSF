//package sg.com.pinder;
//
//import java.io.File;
//import java.net.UnknownHostException;
//
//import sg.com.pinder.pojo.Images;
//
//import com.mongodb.BasicDBObjectBuilder;
//import com.mongodb.DB;
//import com.mongodb.DBCollection;
//import com.mongodb.DBCursor;
//import com.mongodb.DBObject;
//import com.mongodb.MongoClient;
//import com.mongodb.WriteResult;
// 
//public class MongoDBExample implements java.io.Serializable{
// 
//    public static void main(String[] args) throws UnknownHostException {
//     
//        Images image = createImg();
//        DBObject doc = createDBObject(image);
//         
//        MongoClient mongo = new MongoClient("localhost", 27017);
//        DB db = mongo.getDB("test");
//         
//        DBCollection col = db.getCollection("photo");
//         
//        //create user
//        WriteResult result = col.insert(doc);
//        System.out.println(result.getUpsertedId());
//        System.out.println(result.getN());
//        System.out.println(result.isUpdateOfExisting());
//        System.out.println(result.getLastConcern());
//         
//        //read example
//        DBObject query = BasicDBObjectBuilder.start().add("_id", image.getId()).get();
//        DBCursor cursor = col.find(query);
//        while(cursor.hasNext()){
//            System.out.println(cursor.next());
//        }
//         
//        //update example
////        user.setName("Pankaj Kumar");
////        doc = createDBObject(user);
////        result = col.update(query, doc);
////        System.out.println(result.getUpsertedId());
////        System.out.println(result.getN());
////        System.out.println(result.isUpdateOfExisting());
////        System.out.println(result.getLastConcern());
//         
//        //delete example
////        result = col.remove(query);
////        System.out.println(result.getUpsertedId());
////        System.out.println(result.getN());
////        System.out.println(result.isUpdateOfExisting());
////        System.out.println(result.getLastConcern());
//         
//        //close resources
//        mongo.close();
//    }
// 
//    private static DBObject createDBObject(Images image) {
//        BasicDBObjectBuilder docBuilder = BasicDBObjectBuilder.start();
//                                 
//        //docBuilder.append("_id", image.getId());
//        docBuilder.append("name", image.getName());
//        return docBuilder.get();
//    }
// 
//    private static Images createImg() {
//        Images u = new Images();
//        File imageFile = new File("/Users/ravenstorm/Documents/test.jpg");
//        //u.setId(2);
//        u.setName(imageFile);
//        return u;
//    }
//     
//     
// 
//}