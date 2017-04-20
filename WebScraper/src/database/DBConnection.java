package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * This class connects to our database
 * @author Russell
 *
 */
public class DBConnection {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://vergil.u.washington.edu:7713/MileageTracker";
	
	// TODO: change the user to be something other than root
	private static final String USER = "root";
	private static final String PASS = "mysqlcapMilesHelper";
	
	private Connection conn;
	
	public DBConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Returns the credentialID of a valid login, or -1 if login is invalid
	 * @param username the login username
	 * @param password the login password
	 * @return the credentialID of the given username/password combo
	 */
	public int checkLogin(String username, String password) {
		username = sanitize(username);
		password = sanitize(password);
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT PassengerID FROM Credentials WHERE Username=" + username + " AND Password=" + password;
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				return rs.getInt("PassengerID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * Adds a new user to the database
	 * @param username the new users username
	 * @param password the new users password
	 * @param fName first name
	 * @param mName middle name
	 * @param lName last name
	 * @param email the new users email
	 * @return true if successful, false in unsuccessful
	 */
	public boolean createLogin(String username, String password, String fName, String mName, String lName, String email) {
		username = sanitize(username);
		password = sanitize(password);
		fName = sanitize(fName);
		mName = sanitize(mName);
		lName = sanitize(lName);
		email = sanitize(email);
		
		if (checkLogin(username, password) >= 0) {
			return false; // username already taken
		}
		
		try {
			Statement stmt = conn.createStatement();
			
			// I'd prefer to use some kind of proc here for adding user
			
			String sql = "";
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Get the set of login urls associated with an airline
	 * @param airline
	 * @return a list of strings for the urls.  Will usually be length = 1
	 */
	public List<String> getLoginURLs(String airline) {
		return null;
	}
	
	/**
	 * Get saved cookies from previous logins?  This might not be necessary
	 * @param username
	 * @param airline
	 * @return
	 */
	public String getCookies(String username, String airline) {
		return null;
	}
	
	/**
	 * Adds single quotes around user provided strings
	 * Should probably do some other stuff as well...
	 * @param s the string to sanitize
	 * @return a sanitized string
	 */
	private String sanitize(String s) {
		s = "'" + s + "'";
		return s;
	}
}
