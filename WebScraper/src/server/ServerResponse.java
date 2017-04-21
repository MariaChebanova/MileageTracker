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
	
	public ServerResponse() {
		requestType = 0;
		formData = null;
		error = null;
		expDay = -1;
		expMonth = -1;
		expYear = -1;
		points = -1;
	}
	
	public String jsonify() {
		String s = "";
		
		s += "{";
		
		if (error != null) {
			s += "{error:" + error + "}";
		}
		if (requestType != 0) {
			s += "{requestType:" + requestType + "}";
		}
		if (formData != null) {
			s += "{formData:" + formData + "}";
		}
		if (points >= 0) {
			s += "{points:" + points + "}";
		}
		if (expDay >= 0) {
			s += "{expDay:" + expDay + "}";
		}
		if (expMonth >= 0) {
			s += "{expMonth:" + expMonth + "}";
		}
		if (expYear >= 0) {
			s += "{expYear:" + expYear + "}";
		}
		
		s += "}";
		
		return s;
	}
	
	private String sanitize(String s) {
		
		// needs to take care of ", \, /, backspace, formfeed, newline,
		// carriage return, and horizontal tab
		s.replaceAll("\"", "\\\"");
		s.replaceAll("\\", "\\\\");
		s.replaceAll("/", "\\/");
		s.replaceAll("\n", "\\n");
		s.replaceAll("\t", "\\t");
		
		// add quotes to front and back of string
		s = "\"" + s + "\"";
		
		return s;
	}
}
