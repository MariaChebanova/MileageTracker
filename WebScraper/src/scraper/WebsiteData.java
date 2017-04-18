package scraper;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * WebsiteData is an immutable wrapper class for all info necessary to scrape
 * points and expiration from a website, given a username and password
 * It populates itself on construction from the database
 * @author Russell
 *
 */
public class WebsiteData {

	private LinkedList<Request> loginRequests;
	private LinkedList<Request> pointRequests;
	private LinkedList<Request> expRequests;
	
	
	private String loginURL;
	
	private String usernameRequestProperty;
	private String passwordRequestProperty;
	
	private String loginRequestMethod;
	private String userAgentRequestProperty;
	private String acceptLanguageRequestProperty;
	
	private LinkedList<String> variables;
	
	private String pointsURL;
	private String expirationURL;
	
	private String pointsRegex;
	private String expirationRegex;
	
	
	public WebsiteData(String website) {
		//TODO: put some SQL requests here to populate all the fields
		// have to figure out how jdbc works...
		
		
		/*
		loginRequests = new LinkedList<Request>();
		pointRequests = new LinkedList<Request>();
		expRequests = new LinkedList<Request>();
		
		Request r = new Request("https://www.united.com/ual/en/us/account/account/login", "POST");
		r.add("MpNumber");
		r.add("Password");
		
		loginRequests.add(r);
		
		r = new Request("https://www.united.com/ual/en/us/account/security/authquestions", "POST");
		r.add("QuestionsList[0].QuestionKey", );
		
		/*
		loginURL = "https://www.united.com/ual/en/us/account/account/login";
		usernameRequestProperty = "MpNumber";
		passwordRequestProperty = "Password";
		
		pointsURL = "https://www.united.com/web/en-US/apps/account/account.aspx";
		pointsRegex = "(?:<span id=\"ctl00_ContentInfo_AccountSummary_lblMileageBalanceNew\">)(\\p{Digit}+)";
		
		expirationURL = "";
		expirationRegex = "";
		
		variables = new LinkedList<String>();
		
		/*
		loginURL = "https://www.lufthansa.com/online/portal/lh/cxml/04_SD9ePMtCP1I800I_KydQvyHFUBADPmuQy?l=en&cid=1000390&p=LH&s=US";
		usernameRequestProperty = "userid";
		passwordRequestProperty = "password";
		
		pointsURL = "https://www.miles-and-more.com/online/myportal/mam/us/spend?l=en&cid=1000390";
		pointsRegex = "(?:<span id=\"mam-uib-pmiles\">)(\\p{Digit}+)";
		
		expirationURL = "";
		expirationRegex = "";
		
		variables = new LinkedList<String>();
		variables.add("loginType=loginLayer");
		variables.add("portalId=LH");
		variables.add("siteId=US");
		variables.add("current_nodeid=445254021");
		variables.add("current_taxonomy=Homepage2014");
		variables.add("countryId=1000390");
		variables.add("targetTaxonomy=Homepage");
		
		/*
			loginURL = "https://www.playmonopolycodes.us/";
			pointsURL = "https://www.playmonopolycodes.us/enter-codes";
			expirationURL = "https://www.playmonopolycodes.us/enter-codes";
			
			usernameRequestProperty = "email";
			passwordRequestProperty = "password";
			
			pointsRegex = "(?:<span class=\"current-fandango-tokens\">)(\\p{Digit}){1,2}";
			expirationRegex = "";
		*/
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
	
	@SuppressWarnings("unchecked")
	public LinkedList<String> getVariables() {
		return (LinkedList<String>) variables.clone();
	}
}
