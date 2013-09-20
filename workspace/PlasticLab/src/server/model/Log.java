package server.model;

public class Log {
	public static final String TABLE_NAME = "Logs";
	public static final String CONTENT_TIMESTAMP_COLUMN = "timestamp";
	public static final String CONTENT_TABLENUMBER_COLUMN = "tableNumber";
	public static final String CONTENT_GUESTID_COLUMN = "guestId";

	public long timestamp;
	public int tableNumber;
	public int guestId;
	
	public Log() {
	}

}
