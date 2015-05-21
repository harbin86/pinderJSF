package sg.com.pinder.gson;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import org.bson.types.ObjectId;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class ObjectIdTypeAdapter 
{
	
	/**
	 * ObjectIdDeserializer.deserialize
	 * @return Bson.Types.ObjectId
	 */
	public static class ObjectIdDeserializer implements JsonDeserializer<ObjectId> 
	{
		@Override
		public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
		{
			try
			{
				return new ObjectId(json.getAsString());
			}
			catch (Exception e)
			{
				return null;
			}
		}
	}
	
	/**
	 * ObjectIdSerializer.serialize
	 * @return $oid JsonObject from BSON ObjectId
	 */
	public static class ObjectIdSerializer implements JsonSerializer<ObjectId> 
	{
		@Override
		public JsonElement serialize(ObjectId id, Type typeOfT, JsonSerializationContext context)
		{
			return new JsonPrimitive(id.toStringMongod());
		}
	}	
	
//	/**
//	 * DateDeserializer.deserialize
//	 * @return Java.util.Date
//	 */
//	public static class DateDeserializer implements JsonDeserializer<Date> 
//	{
//		@Override
//		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
//			throws JsonParseException
//			{
//				Date d = null;
//				SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//				try {
//					d = f2.parse(json.getAsString());
//				}
//				catch (ParseException e)	
//				{
//					e.printStackTrace();
//					d = null;
//				}
//				return d;
//			}
//	}
//	
//	/**
//	 * DateSerializer.serialize
//	 * @return date JsonElement
//	 */
//	public static class DateSerializer implements JsonSerializer<Date> 
//	{
//		@Override
//		public JsonElement serialize(Date date, Type typeOfT, JsonSerializationContext context)
//		{
//			Date d = (Date)date;
//	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
//			return new JsonPrimitive(format.format(d));
//		}
//	}	
}