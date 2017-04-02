package scraper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class WebsiteScraper {

	private WebsiteData data;
	private List<String> cookies;
	
	public WebsiteScraper() {
		data = new WebsiteData();
	}
	
	/**
	 * Attempts to login to the website with the given username and password
	 * @param username the username to log in with
	 * @param password the password to log in with
	 * @return boolean, whether login was successful
	 */
	public boolean login(String username, String password) {
		
		CookieHandler.setDefault(new CookieManager());
		
		HttpsURLConnection connection = getConnection(data.getLoginURL(), "POST");
		
		connection.setRequestProperty("Connection", "keep-alive");
		
		String parameters = data.getUsernameRequestProperty() + "=" + username + "&" + data.getPasswordRequestProperty() + "=" + password;
		
		connection.setDoOutput(true);
		DataOutputStream s;
		try {
			s = new DataOutputStream(connection.getOutputStream());
			s.writeBytes(parameters);
			s.flush();
			s.close();
			
			int responseCode = connection.getResponseCode();
			System.out.println("response code: " + responseCode);
			if (responseCode != 200) {
				return false;
			}
			
			// prints out the response, probably not needed
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// System.out.println(response.toString());
			
			setCookies(connection.getHeaderFields().get("Set-Cookie"));
			
			return true;
		} catch (IOException e) {
			System.err.println("I/O exception");
			// might want to try a few more times...
			return false;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPoints() {
		HttpsURLConnection connection = getConnection(data.getPointsURL(), "GET");
		connection.setInstanceFollowRedirects(true);
		
		try {
			int responseCode = connection.getResponseCode();
			System.out.println("response code: " + responseCode);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
						
			// try to match the regex
			Pattern p = Pattern.compile(data.getPointsRegex());
			Matcher m = p.matcher(response);
			if (m.find()) {
				return Integer.parseInt(m.group(1));
			}
			
		} catch (IOException e) {
			System.err.println("I/O exception");
			// might want to try a few more times...
		}
		return 0;
	}
	
	//TODO: finish getExpiration method
	public void getExpiration() {
		
	}
	
	
	private HttpsURLConnection getConnection(String urlString, String requestMethod) {
		URL url;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			System.err.println("malformed URL");
			return null;
		}
		
		HttpsURLConnection connection;
		try {
			connection = (HttpsURLConnection) url.openConnection();
		} catch (IOException e) {
			System.err.println("I/O exception");
			// might want to try a few more times...
			return null;
		}
		
		try {
			connection.setRequestMethod(requestMethod);
		} catch (ProtocolException e) {
			System.err.println("Protocol Exception");
			return null;
		}
		
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		
		if (cookies != null) {
			for (String cookie : cookies) {
				connection.addRequestProperty("Cookie", cookie.split(";", 1)[0]);
			}
		}
		
		return connection;
	}
	
	private void setCookies(List<String> cookies) {
		this.cookies = cookies;
	}
}
