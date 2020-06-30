package ro.patzzcode.appointmentPlatform.utils;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class FrontEndException {
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;
	private String status;
	private String error;
	private String message;
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public FrontEndException(Date timestamp, String status, String error, String message) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
	}
}
