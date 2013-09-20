package server.model;

public class Guest {
	
	public static final String TABLE_NAME = "Guests";
	
	public static final String CONTENT_ID_COLUMN = "id";
	public static final String CONTENT_NAME_COLUMN = "name";
	public static final String CONTENT_COMPANY_COLUMN = "company";
	public static final String CONTENT_TITLE_COLUMN = "title";
	public static final String CONTENT_EDUCATION_COLUMN = "education";
	public static final String CONTENT_EMAIL_COLUMN = "email";
	public static final String CONTENT_BIRTHDAY_COLUMN = "birthday";
	
	public int id;
	public String name;
	public String company;
	public String title;
	public String education;
	public String birthday;
	public String email;

	public Guest() {}
	

	public Guest(int id, String name) {
		this.id = id;
		this.name = name;
	}

}
