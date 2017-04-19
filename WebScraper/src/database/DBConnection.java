package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBConnection {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:7713/MileageTracker";
	
	private static final String USER = "root";
	private static final String PASS = "mysqlcapMilesHelper";
	
	private Connection conn;
	
	public DBConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	public int checkLogin(String username, String password) {
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT PassengerID FROM Credentials WHERE Username=" + username + " AND Password=" + password;
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				return rs.getInt("PassengerID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public boolean createLogin(String username, String password, String fName, String mName, String lName, String email) throws SQLException {
		if (checkLogin(username, password) >= 0) {
			return false;
		}
		
		Statement stmt = conn.createStatement();
		String sql = "INSERT INTO Passenger (PassengerFName, PassengerMName, PassengerLName, PassengerEmail) "
					+ "VALUES ('" + fName + "', '" + mName + "', '" + lName + "', '" + email + "');";
		
		return false;
	}
	
	public List<String> getLoginURLs(String airline) {
		return null;
	}
	
	public String getCookies(String username, String airline) {
		return null;
	}
}
