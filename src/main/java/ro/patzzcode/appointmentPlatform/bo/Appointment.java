package ro.patzzcode.appointmentPlatform.bo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "APPOINTMENTS")
public class Appointment implements Comparable<Appointment>{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "client_id")
	private User client;
	private Date creationDate;
	private Date lastUpdate;
	private Date appointmentDate;
	private Date appointmentStartTime;
	private Date appointmentEndTime;
	private String status;
	private boolean valid;
	private String clientFile;
	private String appointmentInterval;
	private String service;
	private String clientMessage;
	private String appointmentDateToString;
	private String fromAdminClientName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public User getClient() {
		return client;
	}
	public void setClient(User client) {
		this.client = client;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getClientFile() {
		return clientFile;
	}
	public void setClientFile(String clientFile) {
		this.clientFile = clientFile;
	}
	public String getAppointmentInterval() {
		return appointmentInterval;
	}
	public void setAppointmentInterval(String appointmentInterval) {
		this.appointmentInterval = appointmentInterval;
	}
	public Date getAppointmentStartTime() {
		return appointmentStartTime;
	}
	public void setAppointmentStartTime(Date date) {
		this.appointmentStartTime = date;
	}
	public Date getAppointmentEndTime() {
		return appointmentEndTime;
	}
	public void setAppointmentEndTime(Date appointmentEndTime) {
		this.appointmentEndTime = appointmentEndTime;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getClientMessage() {
		return clientMessage;
	}
	public void setClientMessage(String clientMessage) {
		this.clientMessage = clientMessage;
	}
	public String getAppointmentDateToString() {
		return appointmentDateToString;
	}
	public void setAppointmentDateToString(String appointmentDateToString) {
		this.appointmentDateToString = appointmentDateToString;
	}
	@Override
	public int compareTo(Appointment o) {
		return getAppointmentStartTime().compareTo(o.getAppointmentStartTime());
	}
	public String getFromAdminClientName() {
		return fromAdminClientName;
	}
	public void setFromAdminClientName(String fromAdminClientName) {
		this.fromAdminClientName = fromAdminClientName;
	}
	
}
