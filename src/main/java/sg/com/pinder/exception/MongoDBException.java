
package sg.com.pinder.exception;

/**
 * @author Han Liang Wee Eric(A0065517)
 * @version
 */
public class MongoDBException extends RuntimeException {
	private static final long serialVersionUID = 2863198945214623293L;
	private String msg;
	private Exception detail;

	/**
	 * @param msg
	 * @param detail
	 */
	public MongoDBException(String msg, Exception detail) {
		this.msg = msg;
		this.detail = detail;
	}

	/**
	 * @return message
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @return details
	 */
	public Exception getDetail() {
		return detail;
	}

}
