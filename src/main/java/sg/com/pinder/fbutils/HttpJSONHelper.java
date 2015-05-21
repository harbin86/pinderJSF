
//HttpJSONHelper.java
//(c) A*STAR IHPC, Eric Han
//Purpose: A helper class to get JSON strings from sources

package sg.com.pinder.fbutils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;



/**
 * A helper class to get JSON strings from sources
 * @author Han Liang Wee, Eric
 * @version 2/28/2013
 */
public class HttpJSONHelper {
	
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
			//System.out.println(uri.toASCIIString());
			
			/*System.out.println(uri.toASCIIString());*/
			
			return new URL(uri.toASCIIString());
		
		//catch exceptions
		} catch (URISyntaxException use) {
			use.getStackTrace();
		} catch (IllegalArgumentException iae) {
			iae.printStackTrace();
		} catch (MalformedURLException mue) {
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
			ioe.printStackTrace();
		} finally {
			//close stream
			try{
				readWebsite.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
		
		return null;
	}

}
