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
			HttpServer s = HttpServer.create(new InetSocketAddress(8000), 0);
			dbc = new DBConnection();
			connections = new HashMap<InetSocketAddress, Integer>();
			s.createContext("/login", new LoginHandler());
			s.createContext("/request", new RequestHandler());
			s.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static class LoginHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			Map<String, String> params = queryToMap(t.getRequestURI().getQuery());
			String response;
			
			if (params.get("username") != null && params.get("password") != null) {
				int passengerID = dbc.checkLogin(params.get("username"), params.get("password"));
				if (passengerID < 0) {
					response = "Invalid username or password";
				} else {
					response = "OK";
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
