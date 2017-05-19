package reminders;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailReminder {

	// Database related constants
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://vergil.u.washington.edu:7713/MileageTracker";
	private static final String USER = "front";
	private static final String PASS = "MileageTrackerFront";
	
	private static final String SQL_QUERY = "SELECT Passenger.PassengerFName, Passenger.PassengerMName, Passenger.PassengerLName, Passenger.PassengerEmail, Passenger.AlertDays, Passenger_Airline.MileageBalance, Passenger_Airline.MileageBalanceExpirationDate, NOW() AS CurrentDate FROM Passenger JOIN Passenger_Airline ON Passenger.PassengerID = Passenger_Airline.PassengerID WHERE Passenger_Airline.MileageBalanceExpirationDate > NOW();";
	
	private static final String USERNAME = "mileage.tracker.capstone";
	private static final String PASSWORD = "capstone2017";
	
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
					String emailTo = rs.getString("Passenger.PassengerEmail");
					String fName = rs.getString("Passenger.PassengerFName");
					String mName = rs.getString("Passenger.PassengerMName");
					String lName = rs.getString("Passenger.PassengerLName");
					int balance = rs.getInt("Passenger_Airline.MileageBalance");
					
					// these points are close to expiring, lets send a reminder!
					String title = "You have miles expiring soon!";
					String message = "Dear " + fName + ",\nYou have a balance of " + balance + " miles expiring on " + expDate + ".\nAct now to make sure your miles don't expire!";
					
					GoogleMail.send(USERNAME, PASSWORD, emailTo, title, message);
				}
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
