package sg.com.pinder.response;

import java.util.List;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class CalendarEventResponse {
	
	private int success;
	private List<CalenderEvent> result;
	
	/**
	 * Creates a Calendar Event Response Object to handle population of Calender Events
	 * @param inResult
	 */
	public CalendarEventResponse(List<CalenderEvent> inResult) {
		this.success = 1;
		this.result = inResult;
	}

	/**
	 * @return the success
	 */
	public int getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(int success) {
		this.success = success;
	}

	/**
	 * @return the result
	 */
	public List<CalenderEvent> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<CalenderEvent> result) {
		this.result = result;
	}
	
}
