package ro.patzzcode.appointmentPlatform.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ro.patzzcode.appointmentPlatform.bo.Appointment;
import ro.patzzcode.appointmentPlatform.bo.User;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
	Page<Appointment> findAll(Pageable pageable);

	Page<Appointment> findByStatusOrderByAppointmentDateToStringDesc(Pageable pageable, String status);
	
	Page<Appointment> findByStatusOrderByIdDesc(Pageable pageable, String status);

	Page<Appointment> findByClientOrderByAppointmentDateToStringDesc(Pageable pageable, User client);
	
	Page<Appointment> findByClientOrderByIdDesc(Pageable pageable, User client);

	Page<Appointment> findByAppointmentDateToStringOrderByIdDesc(Pageable pageable, String appointmentDateToString);

	Page<Appointment> findByClientAndStatusOrderByAppointmentDateToStringDesc(Pageable pageable, User client,
			String status);
	
	Page<Appointment> findByClientAndStatusOrderByIdDesc(Pageable pageable, User client,
			String status);

	Page<Appointment> findByAppointmentDateToStringOrderByAppointmentDateToStringDesc(Pageable pageable,
			String appointmentDateToString);

	Page<Appointment> findByAppointmentDateAndStatusOrderByAppointmentDateToStringDesc(Pageable pageable, Date date,
			String status);

	Page<Appointment> findByAppointmentDateToStringAndStatusOrderByAppointmentDateToStringDesc(Pageable pageable,
			String appointmentDateToString, String status);
	
	Page<Appointment> findByAppointmentDateToStringAndStatusOrderByIdDesc(Pageable pageable,
			String appointmentDateToString, String status);

	List<Appointment> findByAppointmentDateToStringAndValid(String appointmentDateToString, boolean valid);

	List<Appointment> findByClientAndStatusOrderByAppointmentDateToStringDesc(User client, String status);
	
	List<Appointment> findByClientAndStatusOrderByIdDesc(User client, String status);

	Page<Appointment> findByServiceAndValidOrderByAppointmentDateToStringDesc(Pageable pageable, String service,
			boolean valid);
	
	Page<Appointment> findByServiceAndValidOrderByIdDesc(Pageable pageable, String service,
			boolean valid);
	
	Page<Appointment> findByServiceAndValidOrderByAppointmentDateDesc(Pageable pageable, String service,
			boolean valid);

}
