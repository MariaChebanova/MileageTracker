package server;

/**
 * Wrapper class for request responses
 * This class holds all information, and can then be made into json
 * to be sent to the client
 * @author rperry12
 *
 */
public class ServerResponse {

	public boolean loggedIn;
	
	public String error;
	
	
	/*
	 * What kind of request is this?
	 *  'l' : login to airline website
	 *  'p' : get the points from airline
	 *  'e' : get the expiration from airline
	 */
	public char requestType;
	
	public String formData;
	
	public int points;
	
	public int expDay;
	public int expMonth;
	public int expYear;
	
	public String jsonify() {
		
		return null;
	}
}
