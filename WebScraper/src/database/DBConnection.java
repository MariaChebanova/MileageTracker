package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import scraper.Request;

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
		
		Statement stmt;
		String sql;
		ResultSet rs;
		
		try {
			
			// Check if the username is already taken
			stmt = conn.createStatement();
			sql = "SELECT PassengerID FROM Credentials WHERE Username=" + username;
			rs = stmt.executeQuery(sql);
			if (rs.isAfterLast()) {
				return false; //username already taken
			}
			
			// Run the new user proc
			stmt = conn.createStatement();
			sql = "";
			rs = stmt.executeQuery(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Get the login url associated with an airline
	 * @param airline
	 * @return the url of the login page
	 */
	public Request getLoginRequest(String airline) {
		int airlineID = getAirlineID(airline);
		int urlID = getURLID(airlineID, "AirlineLoginURLID");
		Statement stmt;
		String sql;
		ResultSet rs;
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT AirlineURL, AirlineURLMethod, AirlineURLRegex FROM Airline_URL WHERE AirlineURLID=" + urlID;
			rs = stmt.executeQuery(sql);
			if (rs.isAfterLast()) {
				return null;
			}
			
			Request r = new Request();
			
			r.url = rs.getString("AirlineURL");
			r.method = rs.getString("AirlineURLMethod");
			r.regex = null;
			
			return r;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get the point page url associated with an airline
	 * @param airline
	 * @return the url of the point page
	 */
	public Request getPointsRequest(String airline) {
		int airlineID = getAirlineID(airline);
		int urlID = getURLID(airlineID, "AirlinePointsURLID");
		Statement stmt;
		String sql;
		ResultSet rs;
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT AirlineURL, AirlineURLMethod, AirlineURLRegex FROM Airline_URL WHERE AirlineURLID=" + urlID;
			rs = stmt.executeQuery(sql);
			if (rs.isAfterLast()) {
				return null;
			}
			
			Request r = new Request();
			
			r.url = rs.getString("AirlineURL");
			r.method = rs.getString("AirlineURLMethod");
			r.regex = rs.getString("AirlineURLRegex");
			
			return r;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get the expiration page url associated with an airline
	 * @param airling
	 * @return the url of the expiration page
	 */
	public Request getExpirationRequest(String airline) {
		int airlineID = getAirlineID(airline);
		int urlID = getURLID(airlineID, "AirlineExpirationURLID");
		Statement stmt;
		String sql;
		ResultSet rs;
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT AirlineURL, AirlineURLMethod, AirlineURLRegex FROM Airline_URL WHERE AirlineURLID=" + urlID;
			rs = stmt.executeQuery(sql);
			if (rs.isAfterLast()) {
				return null;
			}
			
			Request r = new Request();
			
			r.url = rs.getString("AirlineURL");
			r.method = rs.getString("AirlineURLMethod");
			r.regex = rs.getString("AirlineURLRegex");
			
			return r;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get the expiration format associated with an airline
	 * @param airline
	 * @return the expiration date format
	 */
	public String getExpirationFormat(String airline) {
		int airlineID = getAirlineID(airline);
		Statement stmt;
		String sql;
		ResultSet rs;
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT AirlineExpirationFormat FROM Airline WHERE AirlineID=" + airlineID;
			rs = stmt.executeQuery(sql);
			if (rs.isAfterLast()) {
				return null;
			}
			
			return rs.getString("AirlineExpirationFormat");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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
	 * Get points saved from the database, without scraping the web
	 * @param passengerID
	 * @param airline
	 * @return the number of points, or negative if an error occured
	 */
	public int getPoints(int passengerID, String airline) {
		int airlineID = getAirlineID(airline);
		Statement stmt;
		String sql;
		ResultSet rs;
		
		try {			
			stmt = conn.createStatement();
			sql = "SELECT MileageBalance FROM Passenger_Airline WHERE AirlineID=" + airlineID + " AND PassengerID=" + passengerID;
			rs = stmt.executeQuery(sql);
			if (rs.isAfterLast()) {
				return -1;
			}
			
			return rs.getInt("MileageBalance");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * Get the expiration date saved from the database, without scraping the web
	 * @param passengerID
	 * @param airline
	 * @return the expiration date
	 */
	public Date getExpiration(int passengerID, String airline) {
		int airlineID = getAirlineID(airline);
		Statement stmt;
		String sql;
		ResultSet rs;
		
		try {			
			stmt = conn.createStatement();
			sql = "SELECT MileageBalanceExpirationDate FROM Passenger_Airline WHERE AirlineID=" + airlineID + " AND PassengerID=" + passengerID;
			rs = stmt.executeQuery(sql);
			if (rs.isAfterLast()) {
				return null;
			}
						
			return rs.getDate("MileageBalanceExpirationDate");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get the airline ID from the name of the airline
	 * @param airline
	 * @return airlineID
	 */
	private int getAirlineID(String airline) {
		airline = sanitize(airline);
		
		Statement stmt;
		String sql;
		ResultSet rs;
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT AirlineID FROM Airline WHERE AirlineName=" + airline;
			rs = stmt.executeQuery(sql);
			if (rs.isAfterLast()) {
				return -1;
			}
			
			return rs.getInt("AirlineID");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	private int getURLID(int airlineID, String columnName) {
		Statement stmt;
		String sql;
		ResultSet rs;
		
		try {
			stmt = conn.createStatement();
			sql = "SELECT " + columnName + " FROM Airline WHERE AirlineID=" + airlineID;
			rs = stmt.executeQuery(sql);
			if (rs.isAfterLast()) {
				return -1;
			}
			
			return rs.getInt("MileageBalance");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
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
