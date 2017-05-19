

public class EmailReminderTest {
	private static final String USERNAME = "mileage.tracker.capstone";
	private static final String PASSWORD = "capstone2017";
	
	public static void main(String[] args) {
		try {
			GoogleMail.send(USERNAME, PASSWORD, "rperry12@uw.edu", "test email", "testing email notification");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
