package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import server.model.Guest;
import server.model.Log;
public class DatabaseHelper {
	private static DatabaseHelper mHelper;

	private DatabaseHelper() {
	}
	
	public static DatabaseHelper getInstance() {
		if(mHelper == null) {
			mHelper = new DatabaseHelper();
		}
		
		return mHelper;
	}
	
	private Connection getConnection() throws SQLException {
		SQLiteConfig config = new SQLiteConfig();
		config.setSharedCache(true);
		config.enableRecursiveTriggers(true);
//		config.setUserVersion(1);
		
		SQLiteDataSource ds = new SQLiteDataSource(config);
		ds.setUrl("jdbc:sqlite:plasticLab.db");
		
		return ds.getConnection();
	}
	
	private Statement getStatement() throws SQLException {
		Statement s = getConnection().createStatement();
//		s.setQueryTimeout(30);
		return s;
	}
	
	public void createTablesIfNeed() throws SQLException {
		
		String guestT = "CREATE TABLE " + Guest.TABLE_NAME + " (" +
				Guest.CONTENT_ID_COLUMN + " INTEGER PRIMARY KEY UNIQUE NOT NULL, " +
				Guest.CONTENT_NAME_COLUMN + " TEXT NOT NULL," +
				Guest.CONTENT_COMPANY_COLUMN + " TEXT," +
				Guest.CONTENT_TITLE_COLUMN + " TEXT," +
				Guest.CONTENT_EDUCATION_COLUMN + " TEXT," +
				Guest.CONTENT_EMAIL_COLUMN + " TEXT," +
				Guest.CONTENT_BIRTHDAY_COLUMN + " TEXT" +
						");";
		
		String logT = "CREATE TABLE " + Log.TABLE_NAME + " (" +
				Log.CONTENT_TIMESTAMP_COLUMN + " INTEGER," +
				Log.CONTENT_TABLENUMBER_COLUMN + " INTEGER," +
				Log.CONTENT_GUESTID_COLUMN + " INTEGER," +
				"FOREIGN KEY(" + Log.CONTENT_GUESTID_COLUMN + ") REFERENCES " + Guest.TABLE_NAME + "(" + Guest.CONTENT_ID_COLUMN + "));";
		
		Statement statement = getStatement();
		
		try {
			statement.execute("SELECT 1 FROM " + Guest.TABLE_NAME);
		} catch (Exception e) {
			statement.executeUpdate(guestT);
		}
		
		try {
			statement.execute("SELECT 1 FROM " + Log.TABLE_NAME);
		} catch (Exception e) {
			statement.executeUpdate(logT);
		}

//		statement.getConnection().close();
		statement.close();
	}
	
	public void dropTables() throws SQLException{
		String dropG = "DROP TABLE IF EXISTS " + Guest.TABLE_NAME;
		String dropL = "DROP TABLE IF EXISTS " + Log.TABLE_NAME;
		
		Statement statement = getStatement();
		statement.executeUpdate(dropG);
		statement.executeUpdate(dropL);
//		statement.getConnection().close();
		statement.close();
	}

	public void insertGuest(Guest guest) throws SQLException {
		insertGuest(guest.id, guest.name, guest.company, guest.title, guest.education, guest.birthday, guest.email);
	}
	
	public void insertGuest(int id, String name, String company, String title, String education, String birthday, String email) {
		String insert = "INSERT INTO " + Guest.TABLE_NAME + "(" + 
				Guest.CONTENT_ID_COLUMN + ", " + Guest.CONTENT_NAME_COLUMN + ", " + Guest.CONTENT_COMPANY_COLUMN + ", " + 
				Guest.CONTENT_TITLE_COLUMN+ ", " + Guest.CONTENT_EDUCATION_COLUMN+ ", " + Guest.CONTENT_BIRTHDAY_COLUMN+ ", " + Guest.CONTENT_EMAIL_COLUMN + 
				") values(" + 
				id + ", \"" + name + "\", \"" + company + "\", \"" + title + "\", \"" + education + "\", \"" + birthday + "\", \"" + email + "\")";
		System.out.print(insert);
		
		try {
			insertGuest(insert);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void insertGuest(String staement) throws SQLException {
		Statement statement = getStatement();
		statement.executeUpdate(staement);
//		statement.getConnection().close();
		statement.close();
	}
	
	public void insertLog(String guestId, String tableNumber, long timestamp) throws SQLException {
		insertLog(Integer.parseInt(guestId), Integer.parseInt(tableNumber), timestamp);
	}

	public synchronized void insertLog(int guestId, int tableNumber, long timestamp) throws SQLException {
		String insert = "INSERT INTO " + Log.TABLE_NAME + "("+ Log.CONTENT_TIMESTAMP_COLUMN + ", " + Log.CONTENT_TABLENUMBER_COLUMN + ", " + 
					Log.CONTENT_GUESTID_COLUMN + ") values(" + timestamp + ", " + tableNumber +" ," + guestId + ")";
		Statement statement = getStatement();
		statement.executeUpdate(insert);
//		statement.getConnection().close();
		statement.close();
	}

	public ArrayList<Guest> getGuestList() throws SQLException{
		String query = "SELECT * FROM " + Guest.TABLE_NAME;
		return getGuestList(query);
	}
	
	public ArrayList<Guest> getGuestList(String statement) throws SQLException{
		ArrayList<Guest> list = new ArrayList<Guest>();
		
		Statement st = getStatement();
		ResultSet result = st.executeQuery(statement);
		
		while(result.next()) {
			Guest guest = new Guest();
			guest.id = result.getInt(Guest.CONTENT_ID_COLUMN);
			guest.name = result.getString(Guest.CONTENT_NAME_COLUMN);
			list.add(guest);
		}
		
		result.close();
//		st.getConnection().close();
		st.close();
		return list;
	}
	
	public HashMap<String, Guest> getGuestMap() throws SQLException{
		HashMap<String, Guest> list = new HashMap<String, Guest>();
		
		String query = "SELECT * FROM " + Guest.TABLE_NAME;
		Statement st = getStatement();
		ResultSet result = st.executeQuery(query);
		
		while(result.next()) {
			Guest guest = new Guest();
			guest.id = result.getInt(Guest.CONTENT_ID_COLUMN);
			guest.name = result.getString(Guest.CONTENT_NAME_COLUMN);
			list.put(String.valueOf(guest.id), guest);
		}

		result.close();
//		st.getConnection().close();
		st.close();
		return list;
	}
	
	public HashMap<String, String> getGuestTableMap() throws SQLException{
		HashMap<String, String> list = new HashMap<String, String>();
		
		String query = "SELECT " + Guest.CONTENT_ID_COLUMN + " FROM " + Guest.TABLE_NAME;
		Statement st = getStatement();
		ResultSet result = st.executeQuery(query);
		
		while(result.next()) {
			Guest guest = new Guest();
			guest.id = result.getInt(Guest.CONTENT_ID_COLUMN);
			list.put(String.valueOf(guest.id), PartyFrame.DEFAULT_TABLE_NUMBER);
		}

		result.close();
//		st.getConnection().close();
		st.close();
		return list;
	}
	
	
	public ArrayList<Log> getLogList() throws SQLException{
		String query = "SELECT * FROM " + Log.TABLE_NAME;
		return getLogList(query);
	}
	

	public ArrayList<Log> getLogList(int guestId) throws SQLException{
		String query = "SELECT * FROM " + Log.TABLE_NAME + " WHERE " + Log.CONTENT_GUESTID_COLUMN + "=" + guestId;
		return getLogList(query);
	}
	
	public ArrayList<Log> getLogList(String statment) throws SQLException{
		ArrayList<Log> list = new ArrayList<Log>();
		
		Statement st = getStatement();
		ResultSet result = st.executeQuery(statment);
		
		while(result.next()) {
			Log log = new Log();
			log.timestamp = result.getLong(Log.CONTENT_TIMESTAMP_COLUMN);
			log.tableNumber = result.getInt(Log.CONTENT_TABLENUMBER_COLUMN);
			log.guestId = result.getInt(Log.CONTENT_GUESTID_COLUMN);
			list.add(log);
		}

		result.close();
//		st.getConnection().close();
		st.close();
		return list;
	}
	
	
}
