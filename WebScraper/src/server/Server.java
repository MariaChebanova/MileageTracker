package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import database.DBConnection;

/**
 * Class that handles all server requests from the client
 * @author Russell
 *
 */
public class Server {

	private static DBConnection dbc;
	private static Map<InetSocketAddress, Integer> connections;
	
	public static void main(String[] args) {
		try {
			HttpServer s = HttpServer.create(new InetSocketAddress(7714), 0);
			dbc = new DBConnection();
			connections = new HashMap<InetSocketAddress, Integer>();
			s.createContext("/login", new LoginHandler());
			System.out.println("login page created");
			s.createContext("/request", new RequestHandler());
			System.out.println("request page created");
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
			Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
			String response;
			
			if (params.get("username") != null && params.get("password") != null) {
				int passengerID = dbc.checkLogin(params.get("username"), params.get("password"));
				if (passengerID < 0) {
					if (params.get("fname") != null && params.get("mname") != null && params.get("lname") != null && params.get("email") != null) {
						response = "OK, new login created";
						dbc.createLogin(params.get("username"), params.get("password"), params.get("fname"), params.get("mname"), params.get("lname"), params.get("email"));
					} else {
						response = "";
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
	 *  http:// ~url~ :7714/request?request=X&airline=XXXX
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
			Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
			ServerResponse sr = new ServerResponse();
			
			if (connections.get(t.getRemoteAddress()) == null) {
				sr.loggedIn = false;
				sr.error = "Not logged in";
			} else {
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
						
						
					// points request	
					} else if (request == 'p') {
						sr.requestType = 'p';
						
						
					// expiration request	
					} else if (request == 'e') {
						sr.requestType = 'e';
						
					
					} else {
						sr.error = "Invalid request";
					}
				}
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
