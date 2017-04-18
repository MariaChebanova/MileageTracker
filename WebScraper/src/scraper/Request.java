package scraper;

import java.util.LinkedList;

/**
 * 
 * @author Russell
 *
 */
public class Request {

	public String url;
	public String method;
	public LinkedList<Variable> variables;

	public Request(String url, String method) {
		this.url = url;
		this.method = method;
		this.variables = new LinkedList<Variable>();
	}
	
	public void add(String name, String value) {
		variables.add(new Variable(name, value, false));
	}
	
	public void add(String name) {
		variables.add(new Variable(name, null, true));
	}
}
