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
	 *  'r' : refresh the points/expiration
	 *  'g' : get the points/expiration
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
		error = "";
		expDay = -1;
		expMonth = -1;
		expYear = -1;
		points = -1;
	}
	
	public String jsonify() {
		error = sanitize(error);
		if (formData != null) {
			formData = sanitize(formData);
		}
		
		String s = "";
		
		s += "{";
		
		if (requestType != 0) {
			s += "\"requestType\":" + requestType + ",";
		}
		if (formData != null) {
			s += "\"formData\":" + formData + ",";
		}
		if (points >= 0) {
			s += "\"points\":" + points + ",";
		}
		if (expDay >= 0) {
			s += "\"expDay\":" + expDay + ",";
		}
		if (expMonth >= 0) {
			s += "\"expMonth\":" + expMonth + ",";
		}
		if (expYear >= 0) {
			s += "\"expYear\":" + expYear + ",";
		}
		s += "\"error\":" + error + ",";

		s += "}";
		
		return s;
	}
	
	private String sanitize(String s) {
		
		// needs to take care of ", \, /, backspace, formfeed, newline,
		// carriage return, and horizontal tab
		
		// replace each backslash with two backslashes
		s.replaceAll("\\", "\\\\");

		// replace each quote with escaped quote
		s.replaceAll("\"", "\\\"");
		
		// replace each forward slash with escaped forward slash
		s.replaceAll("/", "\\/");
		
		// replace backspace, formfeed, newline, carriage return, and tab
		s.replaceAll("\b", "\\b");
		s.replaceAll("\f", "\\f");
		s.replaceAll("\n", "\\n");
		s.replaceAll("\r", "\\r");
		s.replaceAll("\t", "\\t");
		
		// add quotes to front and back of string
		s = "\"" + s + "\"";
		
		return s;
	}
}
