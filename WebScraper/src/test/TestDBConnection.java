package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import database.DBConnection;

/**
 * This class connects to our database
 * @author Russell
 *
 */
public class TestDBConnection {

	private Map<String, Integer> logins;
	
	public TestDBConnection() {
		logins = new HashMap<String, Integer>();
		logins.put("dzmudaHello123", 1);
		logins.put("mc511Hello124", 2);
	}
	
	public int checkLogin(String username, String password) {
		Integer r = logins.get(username + password);
		if (r == null) {
			return -1;
		}
		
		return r;
	}
	
	public boolean createLogin(String username, String password, String fName, String mName, String lName, String email) {
		if (logins.get(username + password) != null) {
			return false;
		}
		
		logins.put(username + password, new Random().nextInt(1000));
		return true;
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
