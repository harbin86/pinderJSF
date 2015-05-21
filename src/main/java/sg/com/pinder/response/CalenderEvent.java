package sg.com.pinder.response;

import sg.com.pinder.pojo.EventRecord;

import com.google.gson.annotations.SerializedName;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class CalenderEvent {
	
	private String id;
	private String title;
	private String url;
	@SerializedName("class")
	private String clazz;
	private String start;
	private String end;
	
	/**
	 * Creates a single Calender Event object
	 * @param inRecord
	 */
	public CalenderEvent(EventRecord inRecord) {
		this.id=inRecord.getId().toString();
		this.title=inRecord.getName();
		this.url="/event/"+inRecord.getEventTextId();
		this.clazz="event-info";
		this.start=String.valueOf(inRecord.getStartDate().getTime());
		this.end=String.valueOf(inRecord.getEndDate().getTime());
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the clazz
	 */
	public String getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the start
	 */
	public String getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public String getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	
}
