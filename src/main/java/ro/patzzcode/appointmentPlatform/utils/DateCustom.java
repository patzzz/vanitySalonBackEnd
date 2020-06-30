package ro.patzzcode.appointmentPlatform.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.joda.time.LocalDateTime;

public class DateCustom {
	public static java.time.LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
	    return Instant.ofEpochMilli(dateToConvert.getTime())
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
}
