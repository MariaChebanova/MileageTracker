package scraper;

import java.util.HashMap;
import java.util.LinkedList;

import database.DBConnection;

/**
 * WebsiteData is an wrapper class for all info necessary to scrape
 * points and expiration from a website, given a username and password
 * It populates itself on construction from the database
 * @author Russell
 *
 */
public class WebsiteData {
	
	public Request login;
	public Request points;
	public Request expiration;
	public String expirationFormat;
	
	public WebsiteData(String airline, DBConnection dbc) {
		login = dbc.getLoginRequest(airline);
		points = dbc.getPointsRequest(airline);
		expiration = dbc.getExpirationRequest(airline);
		expirationFormat = dbc.getExpirationFormat(airline);
	}
}
