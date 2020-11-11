package ro.patzzcode.appointmentPlatform.utils;

import java.util.Random;

public class Constants {

	public static final String APPOINTMENT_STATUS_PENDING = "Astept confirmare";	//0
	public static final String APPOINTMENT_STATUS_CONFIRMED = "Confirmata";			//1
	public static final String APPOINTMENT_STATUS_COMPLETED = "Efectuata";			//2
	public static final String APPOINTMENT_STATUS_CANCELED = "Anulata";				//3
	
	
	// user status
	public static final int USER_STATUS_WAIT_OTP = 0;
	public static final int USER_STATUS_VERIFIED = 1;
	
	public static final int CODE_EXP_HOURS = 24;
	public static final int OTP_LEN = 4;
	
	public static String OTP() {
		String numbers = "0123456789";
		StringBuffer s = new StringBuffer();

		Random rndm_method = new Random();

		for (int i = 0; i < OTP_LEN; i++) {
			s.append(numbers.charAt(rndm_method.nextInt(numbers.length())));
		}
		return s.toString();
	}
	
	public static final int SERVICE_TUNS_BARBAT = 30;
	public static final int SERVICE_TUNS_FEMEIE = 90;
	public static final int SERVICE_TUNS_COPII = 30;
	public static final int SERVICE_SPALAT_BARBAT = 10;
	public static final int SERVICE_SPALAT_FEMEIE = 20;
	public static final int SERVICE_COAFAT = 90;
	public static final int SERVICE_VOPSIT_RADACINA = 150;
	public static final int SERVICE_VOPSIT_UNIFORM = 180;
	public static final int SERVICE_BALAYAGE = 270;
	public static final int SERVICE_CORECTAT_DE_CULOARE = 270;
	public static final int SERVICE_SCHIMBARE_DE_CULOARE = 360;
	
	
	public static final String ERROR_STATUS_UNAUTHORIZED = "1";
	public static final String ERROR_ERROR_UNAUTHORIZED = "UNAUTHORIZED";
	public static final String ERROR_MESSAGE_UNAUTHORIZED = "This token is not valid | You need a token to access this method";

	public static final String ERROR_STATUS_USER_NOT_FOUND = "2";
	public static final String ERROR_ERROR_USER_NOT_FOUND = "USER NOT FOUND";
	public static final String ERROR_MESSAGE_USER_NOT_FOUND = "This user does not exist.";

	public static final String ERROR_STATUS_USED_CREDENTIALS = "3";
	public static final String ERROR_ERROR_USED_CREDENTIALS = "USED CREDENTIALS";
	public static final String ERROR_MESSAGE_USED_CREDENTIALS = "Those credentials were already used.";

	public static final String ERROR_STATUS_CREATE_USER = "4";
	public static final String ERROR_ERROR_CREATE_USER = "USER WAS NOT CREATED";
	public static final String ERROR_MESSAGE_CREATE_USER = "The user was not created";
	
	public static final String ERROR_STATUS_OTP_NOT_ACCEPTABLE = "5";
	public static final String ERROR_ERROR_OTP_NOT_ACCEPTABLE = "OTP WAS NOT ACCEPTED";
	public static final String ERROR_MESSAGE_OTP_NOT_ACCEPTABLE = "The otp was not accepted";
	
	public static final String LOGIN_SUCCESFULLY_STATUS = "6";
	public static final String LOGIN_SUCCESFULLY_MESSAGE = "LOGIN SUCCESFULLY";
	
	public static final String ERROR_STATUS_WRONG_CREDENTIALS = "7";
	public static final String ERROR_ERROR_WRONG_CREDENTIALS = "WRONG CREDENTIALS";
	public static final String ERROR_MESSAGE_WRONG_CREDENTIALS = "Those credentials are wrong";
}
