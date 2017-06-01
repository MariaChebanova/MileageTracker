import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TwilioSMS {

	public static final String ACCOUNT_SID = "AC55b57b3e3005cf5b5e07d50d3878b515";
	public static final String AUTH_TOKEN = "c30a61a77ef08537f494206e5da4e7bd";
	public static final String FROM_NUMBER = "+12056866294";
	
	public static String send(final String toNumber, final String messageText) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		
		try {
			Message message = Message.creator(new PhoneNumber("+1" + toNumber), new PhoneNumber(FROM_NUMBER), messageText).create();
			return message.getSid();
		} catch (Exception e) {
			return "error";
		}
	}
	
}
