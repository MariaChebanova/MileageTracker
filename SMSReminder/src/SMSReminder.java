
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SMSReminder {

	// Database related constants
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://vergil.u.washington.edu:7713/MileageTracker";
	private static final String USER = "front";
	private static final String PASS = "MileageTrackerFront";
	
	private static final String SQL_QUERY = "SELECT Passenger.PassengerFName, Passenger.PassengerPhoneNumber, Passenger.AlertDays, Passenger_Airline.MileageBalance, Passenger_Airline.MileageBalanceExpirationDate, Airline.AirlineName, NOW() AS CurrentDate FROM Passenger JOIN Passenger_Airline ON Passenger.PassengerID = Passenger_Airline.PassengerID JOIN Airline ON Airline.AirlineID = Passenger_Airline.AirlineID WHERE Passenger_Airline.MileageBalanceExpirationDate > NOW() AND Passenger.AlertPhone=1;";
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		Connection conn;
		Statement stmt;
		ResultSet rs;
		
		try {
			
			// Connect to the database
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			// execute the query
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL_QUERY);

			// cycle through all of the user data
			while(rs.next()) {
				int alertDays = rs.getInt("Passenger.AlertDays");
				Date expDate = rs.getDate("Passenger_Airline.MileageBalanceExpirationDate");
				Date curDate = rs.getDate("CurrentDate");
				
				curDate.setDate(curDate.getDate() + alertDays);
				if (curDate.after(expDate)) {
					
					// get the remaining info about this passenger
					String textTo = rs.getString("Passenger.PassengerPhoneNumber");
					String fName = rs.getString("Passenger.PassengerFName");
					String airline = rs.getString("Airline.AirlineName");
					int balance = rs.getInt("Passenger_Airline.MileageBalance");
					
					// these points are close to expiring, lets send a reminder!
					String message = "\n\nDear " + fName + ",\nYou have a balance of " + balance + " miles with the airline " + airline + ", expiring on " + expDate + ".\nAct now to make sure your miles don't expire!";
					
					String sid = TwilioSMS.send(textTo, message);
					System.out.println("text sent to " + textTo + ", with sid " + sid);
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
