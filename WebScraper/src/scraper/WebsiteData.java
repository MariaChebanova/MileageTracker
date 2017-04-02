package scraper;

/**
 * WebsiteData is a wrapper class for all info necessary to scrape
 * points and expiration from a website, given a username and password
 * It populates itself on construction from the database
 * @author Russell
 *
 */
public class WebsiteData {

	private String loginURL;
	
	private String usernameRequestProperty;
	private String passwordRequestProperty;
	
	private String loginRequestMethod;
	private String userAgentRequestProperty;
	private String acceptLanguageRequestProperty;
	
	
	private String pointsURL;
	private String expirationURL;
	
	private String pointsRegex;
	private String expirationRegex;
	
	
	public WebsiteData() {
		//TODO: put some SQL requests here to populate all the fields
		//TODO: have a parameter string to get the right fields from the db
		
		loginURL = "https://www.playmonopolycodes.us/";
		pointsURL = "https://www.playmonopolycodes.us/enter-codes";
		expirationURL = "https://www.playmonopolycodes.us/enter-codes";
		
		usernameRequestProperty = "email";
		passwordRequestProperty = "password";
		
		pointsRegex = "(?:<span class=\"current-fandango-tokens\">)(\\p{Digit}){1,2}";
		expirationRegex = "";
	}
	
	public String getLoginURL() {
		return loginURL;
	}
	
	public String getPointsURL() {
		return pointsURL;
	}
	
	public String getExpirationURL() {
		return expirationURL;
	}
	
	public String getUsernameRequestProperty() {
		return usernameRequestProperty;
	}
	
	public String getPasswordRequestProperty() {
		return passwordRequestProperty;
	}
	
	public String getPointsRegex() {
		return pointsRegex;
	}
	
	public String getExpirationRegex() {
		return expirationRegex;
	}
}
