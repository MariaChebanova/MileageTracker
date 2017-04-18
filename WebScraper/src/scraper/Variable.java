package scraper;

/**
 * Basic container for holding variables
 * @author Russell
 *
 */
public class Variable {
	public String name;
	public String value;
	public boolean requested;
	
	public Variable(String name, String value, boolean requested) {
		this.name = name;
		this.value = value;
		this.requested = requested;
	}
}
