package ro.patzzcode.appointmentPlatform.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "USERS")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date lastUpdate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfBirth;
	private String mail;
	private boolean valid;
	private int appointmentsCompleted;
	private int appointmentsCanceled;
	private int blacklistCount;
//	@ManyToOne
//	@JoinColumn(name = "lastAppointment_id")
//	private Appointment lastAppointment;
	private boolean onBlacklist;
	private boolean isAdmin;
	private int status;
	private String sex;
	private String registerCode;
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerCodeDate;
	private String phoneNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public int getAppointmentsCompleted() {
		return appointmentsCompleted;
	}
	public void setAppointmentsCompleted(int appointmentsCompleted) {
		this.appointmentsCompleted = appointmentsCompleted;
	}
	public int getAppointmentsCanceled() {
		return appointmentsCanceled;
	}
	public void setAppointmentsCanceled(int appointmentsCanceled) {
		this.appointmentsCanceled = appointmentsCanceled;
	}
	public int getBlacklistCount() {
		return blacklistCount;
	}
	public void setBlacklistCount(int blacklistCount) {
		this.blacklistCount = blacklistCount;
	}
	public boolean isOnBlacklist() {
		return onBlacklist;
	}
	public void setOnBlacklist(boolean onBlacklist) {
		this.onBlacklist = onBlacklist;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public String getRegisterCode() {
		return registerCode;
	}
	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
	public Date getRegisterCodeDate() {
		return registerCodeDate;
	}
	public void setRegisterCodeDate(Date registerCodeDate) {
		this.registerCodeDate = registerCodeDate;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
//	public Appointment getLastAppointment() {
//		return lastAppointment;
//	}
//	public void setLastAppointment(Appointment lastAppointment) {
//		this.lastAppointment = lastAppointment;
//	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
