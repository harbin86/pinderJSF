
//HttpJSONHelper.java
//(c) A*STAR IHPC, Eric Han
//Purpose: A helper class to get JSON strings from sources

package sg.com.pinder.webhandler;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * A helper class to get JSON strings from sources
 * @author Han Liang Wee, Eric
 * @version 2/28/2013
 */
public class HttpJSONHelper {
	
	static Logger logger = Logger.getLogger(HttpJSONHelper.class);
	
	/**
	 * Retrieve the JSON string response given a query from the Solr server
	 * @param query Query to send to Solr server
	 * @return The JSON string from the server's response
	 */
	public static String getJSONFromSolr(String query)
	{
		//return initJSON(initURL("10.217.168.140", 8983, "/solr/scl1/browse", query));
		return initJSON(initURL("localhost", 8983, "/solr/collection1/browse", query));
	}

	/**
	 * Retrieve the JSON string response given a country and address from the Google API server
	 * @param country The country to which the address is referring to
	 * @param address The address to use to query Google's server
	 * @return The JSON string from the server's response 
	 */
	public static String getJSONFromGoogleMapsAPI(String country, String address)
	{
		return initJSON(initURL("maps.googleapis.com", 80, "/maps/api/geocode/json", "sensor=false&address="+country+" "+address));
	}
	
	/**
	 * Retrieve the JSON string response given a few parameters from the Google API server
	 * <br>Extracted from <a href="https://developers.google.com/maps/documentation/directions/#RequestParameters">Google Directions API</a>
	 * @param origin The address or textual latitude/longitude value from which you wish to calculate directions. 
	 * If you pass an address as a string, the Directions service will geocode the string and convert it to a 
	 * latitude/longitude coordinate to calculate directions. If you pass coordinates, ensure that no space exists 
	 * between the latitude and longitude values.
	 * @param destination The address or textual latitude/longitude value from which you wish to calculate directions. 
	 * If you pass an address as a string, the Directions service will geocode the string and convert it to a 
	 * latitude/longitude coordinate to calculate directions. If you pass coordinates, ensure that no space exists 
	 * between the latitude and longitude values.
	 * @param regionCode The region code, specified as a ccTLD ("top-level domain") two-character value. 
	 * (For more information see <a href="https://developers.google.com/maps/documentation/directions/#RegionBiasing"> Region Biasing</a>)
	 * @param sensor Indicates whether or not the directions request comes from a device with a location sensor. 
	 * This value must be either true or false.
	 * @return The JSON string from the server's response
	 */
	public static String getJSONFromGoogleDirectionsAPI(String origin, String destination, String regionCode, boolean sensor)
	{
		return getJSONFromGoogleDirectionsAPI(origin, destination, regionCode, sensor, "");
	}
	
	/**
	 * Retrieve the JSON string response given a few parameters from the Google API server
	 * <br>Extracted from <a href="https://developers.google.com/maps/documentation/directions/#RequestParameters">Google Directions API</a>
	 * @param origin The address or textual latitude/longitude value from which you wish to calculate directions. 
	 * If you pass an address as a string, the Directions service will geocode the string and convert it to a 
	 * latitude/longitude coordinate to calculate directions. If you pass coordinates, ensure that no space exists 
	 * between the latitude and longitude values.
	 * @param destination The address or textual latitude/longitude value from which you wish to calculate directions. 
	 * If you pass an address as a string, the Directions service will geocode the string and convert it to a 
	 * latitude/longitude coordinate to calculate directions. If you pass coordinates, ensure that no space exists 
	 * between the latitude and longitude values.
	 * @param regionCode The region code, specified as a ccTLD ("top-level domain") two-character value. 
	 * (For more information see <a href="https://developers.google.com/maps/documentation/directions/#RegionBiasing"> Region Biasing</a>)
	 * @param sensor Indicates whether or not the directions request comes from a device with a location sensor. 
	 * This value must be either true or false.
	 * @param optionalParm See <a href="https://developers.google.com/maps/documentation/directions/#RequestParameters"> Request Parameters</a>
	 * @return The JSON string from the server's response
	 */
	public static String getJSONFromGoogleDirectionsAPI(String origin, String destination, String regionCode, boolean sensor, String optionalParm)
	{
		return initJSON(initURL("maps.googleapis.com", 80, "/maps/api/directions/json", "origin="+origin+"&destination="+destination+"&region="+regionCode+optionalParm+"&sensor="+Boolean.toString(sensor)));
	}
	
	/**
	 * Initialize the Url from the Uri of the object.
	 * @param host Specify the Host of the server
	 * @param port Specify the port of the server to connect to
	 * @param path Specify the path of the Service on the server
	 * @param query The query that you will use to query the server
	 * @return URL object representing the config that you have set
	 */
	public static URL initURL(String host, int port, String path, String query)
	{
		 
		try{
					
			//create temporary URI object
			URI uri = new URI("http", null, host, port, path, query, null);
			
			//address MAY contain illegal characters
			//eg: "Königstraße, Berlin"
			//convert illegal characters to ASCII
//			System.out.println(uri.toASCIIString());
/*			
			System.out.println(uri.toASCIIString());
			System.out.println("Press enter to continue...");
			Scanner keyboard = new Scanner(System.in);
			keyboard.nextLine();
			*/
			return new URL(uri.toASCIIString());
		
		//catch exceptions
		} catch (URISyntaxException use) {
			logger.fatal("URI Exception: poor syntax");
			use.getStackTrace();
		} catch (IllegalArgumentException iae) {
            logger.fatal("URI Exception: illegal argument");
			iae.printStackTrace();
		} catch (MalformedURLException mue) {
			logger.fatal("URL Exception: bad URL");
            mue.printStackTrace(); 
        }
		
		return null;

	}

	/**
	 * Query server and get JSON
	 * @param url URL object representing the source where it should get the JSON String
	 * @return JSON String, else <code>null</code> if failed.
	 */
	public static String initJSON(URL url)
	{

		BufferedReader readWebsite=null;
		try{

			//declare temporary variables
			
			StringBuilder strBuilderJSON;

			//open and read from stream
			readWebsite=new BufferedReader(new InputStreamReader(new BufferedInputStream(url.openStream())));
			
			//temporary read in variable			
			strBuilderJSON = new StringBuilder();
			String tempJSON;
			
			//read from stream
			while ((tempJSON = readWebsite.readLine()) != null) 
			{
				strBuilderJSON.append(tempJSON).append("\n");
			}
			
			//cast to string
			return new String(strBuilderJSON.toString());

		//catch exceptions
		} catch (IOException ioe) {
			logger.fatal("IO exception");
			ioe.printStackTrace();
		} finally {
			//close stream
			try{
				readWebsite.close();
			} catch (IOException ioe) {
				logger.fatal("Stream Exception: No Stream to Close");
				ioe.printStackTrace();
			}
		}
		
		return null;
	}

}
