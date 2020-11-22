package ro.patzzcode.appointmentPlatform.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ro.patzzcode.appointmentPlatform.bo.Appointment;
import ro.patzzcode.appointmentPlatform.bo.User;
import ro.patzzcode.appointmentPlatform.repositories.AppointmentRepository;
import ro.patzzcode.appointmentPlatform.utils.Constants;

@Service
public class AppointmentService {

	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@SuppressWarnings("deprecation")
	public Appointment createAppointment(Appointment appointment, User client) {
		appointment.setClient(client);
		appointment.setStatus(Constants.APPOINTMENT_STATUS_PENDING);
		appointment.setCreationDate(new Date());
		appointment.setLastUpdate(new Date());
		appointment.setAppointmentInterval(appointment.getAppointmentInterval());
		appointment.setAppointmentDate(appointment.getAppointmentDate());
		String startHour = appointment.getAppointmentInterval().substring(0,2);
		String startMinute = appointment.getAppointmentInterval().substring(3,5);
		String endHour = appointment.getAppointmentInterval().substring(8,10);
		String endMinute = appointment.getAppointmentInterval().substring(11,13);
		Date startDateTime = new Date(appointment.getAppointmentDate().getYear(), appointment.getAppointmentDate().getMonth(), appointment.getAppointmentDate().getDate(), Integer.parseInt(startHour), Integer.parseInt(startMinute));
		Date endDateTime = new Date(appointment.getAppointmentDate().getYear(), appointment.getAppointmentDate().getMonth(), appointment.getAppointmentDate().getDate(), Integer.parseInt(endHour), Integer.parseInt(endMinute));
		appointment.setAppointmentStartTime(startDateTime);
		appointment.setAppointmentEndTime(endDateTime);
		String workaround = new SimpleDateFormat("yyyy-MM-dd").format(appointment.getAppointmentDate());
		appointment.setAppointmentDateToString(workaround);
		appointment.setValid(true);
		appointment = appointmentRepository.save(appointment);
		
		if(!(appointment.getService().equals("DAY OFF") || appointment.getService().equals("BREAK BETWEEN APPOINTMENTS") )) {
			Appointment breakAppointment = new Appointment();
			breakAppointment.setStatus(Constants.APPOINTMENT_STATUS_CONFIRMED);
			breakAppointment.setCreationDate(new Date());
			breakAppointment.setLastUpdate(new Date());
			Date breakEndTime = DateUtils.addMinutes(endDateTime, 15);
			breakAppointment.setAppointmentStartTime(endDateTime);
			breakAppointment.setAppointmentEndTime(breakEndTime);
			breakAppointment.setAppointmentDateToString(workaround);
			breakAppointment.setValid(true);
			breakAppointment.setService("BREAK BETWEEN APPOINTMENTS");
			appointmentRepository.save(breakAppointment);
		}
//		System.out.println("APPOINTMENT DATE: "+appointment.getAppointmentDate());
//		System.out.println(startHour + ":" + startMinute +" - " + endHour +":"+endMinute);
//		System.out.println("START TIME: " + startDateTime.getHours() + ":" + startDateTime.getMinutes());
//		System.out.println("END   TIME: " + endDateTime.getHours() + ":" + endDateTime.getMinutes());
//		System.out.println("START TIME: " + appointment.getAppointmentStartTime().getHours() + ":" +  appointment.getAppointmentStartTime().getMinutes());
//		System.out.println("END   TIME: " + appointment.getAppointmentEndTime().getHours() + ":" +  appointment.getAppointmentEndTime().getMinutes());
		return appointment;
	}
	
	public Appointment updateStatus(Appointment appointment, int statusID) {
		if(statusID == 1) {
			appointment.setStatus(Constants.APPOINTMENT_STATUS_CONFIRMED);
			appointment.setValid(true);
		}else if(statusID == 2) {
			appointment.setStatus(Constants.APPOINTMENT_STATUS_COMPLETED);
			appointment.setValid(true);
		}else if(statusID == 3) {
			appointment.setStatus(Constants.APPOINTMENT_STATUS_CANCELED);
			appointment.setValid(false);
		}
		appointment.setLastUpdate(new Date());
		appointmentRepository.save(appointment);
		return appointment;
	}
	
	public String returnInterval(Appointment appointment) {
		String interval = new String();
		String startHour = appointment.getAppointmentStartTime().toString().substring(11,13);
		String startMinute = appointment.getAppointmentStartTime().toString().substring(14,16);
		String endHour = appointment.getAppointmentEndTime().toString().substring(11,13);
		String endMinute = appointment.getAppointmentEndTime().toString().substring(14,16);
		interval = startHour + ":" + startMinute + " - " + endHour + ":" + endMinute;
		return interval;
	}
}
