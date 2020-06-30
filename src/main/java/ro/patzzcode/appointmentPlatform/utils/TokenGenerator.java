package ro.patzzcode.appointmentPlatform.utils;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Minutes;


public class TokenGenerator {
	protected static SecureRandom random = new SecureRandom();

	public static synchronized String generateToken(String username) {
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		StringBuilder sb = new StringBuilder(700);
		Random random = new Random();
		for (int i = 0; i < 700; i++) {
		    char c = chars[random.nextInt(chars.length)];
		    sb.append(c);
		}
		return sb.toString();
	}
	
	public static boolean isTokenValid(Date tokenDate, int validTime) {
		DateTime today = new DateTime();
		DateTime token = new DateTime(tokenDate);
		if (Minutes.minutesBetween(token, today).getMinutes() <= validTime) {
			return true;
		} else {
			return false;
		}
		
	}
}
