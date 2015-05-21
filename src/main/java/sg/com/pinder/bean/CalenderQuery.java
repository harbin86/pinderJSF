/**
 * A class to do queries to the server, this is a bean, of course.
 */
package sg.com.pinder.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import sg.com.pinder.database.SolrMongoDatabase;
import sg.com.pinder.pojo.EventRecord;
import sg.com.pinder.response.CalendarEventResponse;
import sg.com.pinder.response.CalenderEvent;
import sg.com.pinder.response.SolrResponse;
import sg.com.pinder.web.WebService;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class CalenderQuery extends WebService {
	
	public static Gson gson = new Gson();

	private static final Logger logger = Logger.getLogger(CalenderQuery.class);
	
	private String queryJson;
	
    public void queryAll() {
		SolrQuery params = new SolrQuery();
		params.setQuery("*:*");
		params.setRows(10000);
		logger.debug("Querying Solr:"+params.toString());
		SolrResponse<EventRecord> response = CalenderQuery.eventDB.searchSolrByQuery(params);
		List<EventRecord> eventResult = response.getDocuments();
		List<CalenderEvent> calEventResult =  new ArrayList<CalenderEvent>();
		for(EventRecord eaRecord:eventResult) {
			calEventResult.add(new CalenderEvent(eaRecord));
		}
		CalendarEventResponse queryResult = new CalendarEventResponse(calEventResult);
		queryJson=CalenderQuery.gson.toJson(queryResult);
    }
    
    public void renderJson() throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        externalContext.setResponseContentType("application/json");
        externalContext.setResponseCharacterEncoding("UTF-8");
        queryAll();
        externalContext.getResponseOutputWriter().write(queryJson);
        facesContext.responseComplete();
    }
}
