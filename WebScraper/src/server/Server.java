package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import database.DBConnection;
import scraper.WebsiteData;
import scraper.WebsiteScraper;
import test.TestDBConnection;

/**
 * Class that handles all server requests from the client
 * @author Russell
 *
 */
public class Server {

	// change TestDBConnection to DBConnection for actual results
	private static DBConnection dbc;
	private static Map<InetSocketAddress, Integer> connections;
	
	public static void main(String[] args) {
		try {
			HttpServer s = HttpServer.create(new InetSocketAddress(7714), 100);
			dbc = new DBConnection();
			connections = new HashMap<InetSocketAddress, Integer>();
			s.createContext("/login", new LoginHandler());
			System.out.println("login page created");
			s.createContext("/query", new RequestHandler());
			System.out.println("query page created");
			s.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Handles logging in and creating new accounts
	 * can be accessed with the URI
	 * 
	 *  http:// ~url~ :7714/login?username=XXXX&password=XXXX
	 * 
	 *  http:// ~url~ :7714/login?username=XXXX&password=XXXX&fname=XXXX&mname=XXXX&lname=XXXX&email=XXXX
	 * 
	 * where XXXX is the value, and ~url~ is an as of yet undetermined url
	 */
	static class LoginHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			System.out.println("handling login request");
			
			Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
			System.out.println(params);
			String response;
			
			if (params.get("username") != null && params.get("password") != null) {
				int passengerID = dbc.checkLogin(params.get("username"), params.get("password"));
				if (passengerID < 0) {
					if (params.get("fname") != null && params.get("mname") != null && params.get("lname") != null && params.get("email") != null) {
						response = "OK, new login created";
						dbc.createLogin(params.get("username"), params.get("password"), params.get("fname"), params.get("mname"), params.get("lname"), params.get("email"));
					} else {
						response = "Incorrect username or password";
					}
				} else {
					response = "OK, login successful";
					connections.put(t.getRemoteAddress(), passengerID);
				}
			} else {
				response = "Invalid request format";
			}
			
			t.sendResponseHeaders(200, response.length());
			OutputStream os = (OutputStream) t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
		
	}
	
	/**
	 * Handles requests
	 * can be accessed with the URI
	 * 
	 *  http:// ~url~ :7714/query?request=X&airline=XXXX
	 * 
	 * where XXXX is a valid airline, and X is a request type
	 * 
	 * Fails if the client has not yet logged in.
	 * 
	 * Always responds with some json, even in failure
	 * @author rperry12
	 *
	 */
	static class RequestHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) {
			System.out.println("handling query request");
			
			Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
			System.out.println(params);
			ServerResponse sr = new ServerResponse();
			
			if (connections.get(t.getRemoteAddress()) == null) {
				sr.loggedIn = false;
				sr.error = "Not logged in";
			} else {
				int passengerID = connections.get(t.getRemoteAddress());
				sr.loggedIn = true;
				
				if (params.get("request") == null) {
					sr.error = "No request";
				} else {
					char request = params.get("request").charAt(0);
					String airline = params.get("airline");
					
					if (airline == null) {
						sr.error = "No airline";
						
					// login request	
					} else if (request == 'l') {
						sr.requestType = 'l';
						
						
						
					// get points request	
					} else if (request == 'g') {
						sr.requestType = 'g';
						sr.points = dbc.getPoints(passengerID, airline);
						Date expDate = dbc.getExpiration(passengerID, airline);
						
						sr.expDay = expDate.getDate();
						sr.expMonth = expDate.getMonth();
						sr.expYear = expDate.getYear();
						
					// refresh points request
					} else if (request == 'r') {
						sr.requestType = 'r';
						
						WebsiteData data = new WebsiteData(airline, dbc);
						WebsiteScraper ws = new WebsiteScraper(data);
						
					
					} else {
						sr.error = "Invalid request";
					}
				}
			}
			String response = sr.jsonify();
			
			try {
				t.sendResponseHeaders(200, response.length());
				OutputStream os = (OutputStream) t.getResponseBody();
				os.write(response.getBytes());
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Converts a URI to a map of parameters
	 * Copied from stack overflow
	 * @param query The URI containing some values
	 * @return Map of parameters
	 */
	public static Map<String, String> queryToMap(String query){
	    Map<String, String> result = new HashMap<String, String>();
	    if (query == null) {
	    	return result;
	    }
	    for (String param : query.split("&")) {
	        String pair[] = param.split("=");
	        if (pair.length>1) {
	            result.put(pair[0], pair[1]);
	        }else{
	            result.put(pair[0], "");
	        }
	    }
	    return result;
	}

}
