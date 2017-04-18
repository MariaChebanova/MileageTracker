package server;

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
	
	public ServerResponse() {
		
	}
	
	public String jsonify() {
		
		return null;
	}
}
