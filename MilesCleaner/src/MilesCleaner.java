import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MilesCleaner {
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://vergil.u.washington.edu:7713/MileageTracker";
	private static final String USER = "front";
	private static final String PASS = "MileageTrackerFront";
	
	private static final String SQL_QUERY = "DELETE FROM Passenger_Airline WHERE MileageBalanceExpirationDate < NOW()";
	
	public static void main(String[] args) {
		Connection conn;
		Statement stmt;		
			
		// Connect to the database
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// execute the deletion
			stmt = conn.createStatement();			
			stmt.executeUpdate(SQL_QUERY);
		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
