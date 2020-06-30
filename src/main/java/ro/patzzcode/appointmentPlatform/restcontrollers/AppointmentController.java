package ro.patzzcode.appointmentPlatform.restcontrollers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import ro.patzzcode.appointmentPlatform.bo.Appointment;
import ro.patzzcode.appointmentPlatform.bo.User;
import ro.patzzcode.appointmentPlatform.repositories.AppointmentRepository;
import ro.patzzcode.appointmentPlatform.repositories.UserRepository;
import ro.patzzcode.appointmentPlatform.service.AppointmentService;
import ro.patzzcode.appointmentPlatform.utils.Constants;
import ro.patzzcode.appointmentPlatform.utils.FrontEndException;

@RestController
public class AppointmentController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private AppointmentRepository appointmentRepository;

	@ApiOperation(value = "registerAppointment")
	@RequestMapping(value = "/api/appointment/registerAppointment", method = RequestMethod.POST)
	public ResponseEntity<Object> registerAppointment(@RequestBody Appointment appointment, @RequestParam long userID) {
		try {
			User user = userRepository.findById(userID).orElse(null);
			if (user != null) {
				if (appointment != null) {
					appointment = appointmentService.createAppointment(appointment, user);
					return new ResponseEntity<Object>(appointment, HttpStatus.CREATED);
				}
				return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getAppointment")
	@RequestMapping(value = "/api/appointment/getAppointment", method = RequestMethod.GET)
	public ResponseEntity<Object> getAppointment(@RequestParam long appointmentID) {
		try {
			Appointment appointment = appointmentRepository.findById(appointmentID).orElse(null);
			if (appointment != null) {
				return new ResponseEntity<Object>(appointment, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getAllAppointments")
	@RequestMapping(value = "/api/appointment/getAllAppointments", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllAppointments(@RequestParam int page, @RequestParam int size) {
		try {
			Page<Appointment> appointments = appointmentRepository.findAll(PageRequest.of(page, size));
			return new ResponseEntity<Object>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getTodaysAppointments")
	@RequestMapping(value = "/api/appointment/getTodaysAppointments", method = RequestMethod.GET)
	public ResponseEntity<Object> getTodaysAppointments(@RequestParam int page, @RequestParam int size) {
		try {
			Date date = new Date();
			String dateToString = new SimpleDateFormat("yyyy-MM-dd").format(date);
			Page<Appointment> appointments = appointmentRepository
					.findByAppointmentDateToStringOrderByAppointmentDateToStringDesc(PageRequest.of(page, size),
							dateToString);
			return new ResponseEntity<Object>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getConfirmedAppointmentsOnDate")
	@RequestMapping(value = "/api/appointment/getConfirmedAppointmentsOnDate", method = RequestMethod.GET)
	public ResponseEntity<Object> getConfirmedAppointmentsOnDate(@RequestParam int page, @RequestParam int size,
			@RequestParam String date) {
		try {
//			String dateToString = new SimpleDateFormat("yyyy-MM-dd").format(date);
			Page<Appointment> appointments = appointmentRepository
					.findByAppointmentDateToStringAndStatusOrderByAppointmentDateToStringDesc(
							PageRequest.of(page, size), date, Constants.APPOINTMENT_STATUS_CONFIRMED);
			return new ResponseEntity<Object>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getAllPendingAppointments")
	@RequestMapping(value = "/api/appointment/getAllPendingAppointments", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllPendingAppointments(@RequestParam int page, @RequestParam int size) {
		try {
			Page<Appointment> appointments = appointmentRepository.findByStatusOrderByAppointmentDateToStringDesc(
					PageRequest.of(page, size), Constants.APPOINTMENT_STATUS_PENDING);
			return new ResponseEntity<Object>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getAllConfirmedAppointments")
	@RequestMapping(value = "/api/appointment/getAllConfirmedAppointments", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllConfirmedAppointments(@RequestParam int page, @RequestParam int size) {
		try {
			Page<Appointment> appointments = appointmentRepository.findByStatusOrderByAppointmentDateToStringDesc(
					PageRequest.of(page, size), Constants.APPOINTMENT_STATUS_CONFIRMED);
			return new ResponseEntity<Object>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getAllCancelledAppointments")
	@RequestMapping(value = "/api/appointment/getAllCancelledAppointments", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllCancelledAppointments(@RequestParam int page, @RequestParam int size) {
		try {
			Page<Appointment> appointments = appointmentRepository.findByStatusOrderByAppointmentDateToStringDesc(
					PageRequest.of(page, size), Constants.APPOINTMENT_STATUS_CANCELED);
			return new ResponseEntity<Object>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getAllCompletedAppointments")
	@RequestMapping(value = "/api/appointment/getAllCompletedAppointments", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllCompletedAppointments(@RequestParam int page, @RequestParam int size) {
		try {
			Page<Appointment> appointments = appointmentRepository.findByStatusOrderByAppointmentDateToStringDesc(
					PageRequest.of(page, size), Constants.APPOINTMENT_STATUS_COMPLETED);
			return new ResponseEntity<Object>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getPendingAppointmentsOnDate")
	@RequestMapping(value = "/api/appointment/getPendingAppointmentsOnDate", method = RequestMethod.GET)
	public ResponseEntity<Object> getPendingAppointmentsOnDate(@RequestParam int page, @RequestParam int size,
			@RequestParam String date) {
		try {
//			String dateToString = new SimpleDateFormat("yyyy-MM-dd").format(date);
			Page<Appointment> appointments = appointmentRepository
					.findByAppointmentDateToStringAndStatusOrderByAppointmentDateToStringDesc(
							PageRequest.of(page, size), date, Constants.APPOINTMENT_STATUS_PENDING);
			return new ResponseEntity<Object>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getCancelledAppointmentsOnDate")
	@RequestMapping(value = "/api/appointment/getCancelledAppointmentsOnDate", method = RequestMethod.GET)
	public ResponseEntity<Object> getCancelledAppointmentsOnDate(@RequestParam int page, @RequestParam int size,
			@RequestParam String date) {
		try {
			if (date != null) {
				Page<Appointment> appointments = appointmentRepository
						.findByAppointmentDateToStringAndStatusOrderByAppointmentDateToStringDesc(
								PageRequest.of(page, size), date, Constants.APPOINTMENT_STATUS_CANCELED);
				return new ResponseEntity<Object>(appointments, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getCompletedAppointmentsOnDate")
	@RequestMapping(value = "/api/appointment/getCompletedAppointmentsOnDate", method = RequestMethod.GET)
	public ResponseEntity<Object> getCompletedAppointmentsOnDate(@RequestParam int page, @RequestParam int size,
			@RequestParam String date) {
		try {
//			String dateToString = new SimpleDateFormat("yyyy-MM-dd").format(date);
			Page<Appointment> appointments = appointmentRepository
					.findByAppointmentDateToStringAndStatusOrderByAppointmentDateToStringDesc(
							PageRequest.of(page, size), date, Constants.APPOINTMENT_STATUS_COMPLETED);
			return new ResponseEntity<Object>(appointments, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getAllAppointmentsOfUser")
	@RequestMapping(value = "/api/appointment/getAllAppointmentsOfUser", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllAppointmentsOfUser(@RequestParam int page, @RequestParam int size,
			@RequestParam long userID) {
		try {
			User user = userRepository.findById(userID).orElse(null);
			if (user != null) {
				Page<Appointment> appointments = appointmentRepository
						.findByClientOrderByAppointmentDateToStringDesc(PageRequest.of(page, size), user);
				return new ResponseEntity<Object>(appointments, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(
						new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
								Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND),
						HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getAllAppointmentsOfUserWithStatus")
	@RequestMapping(value = "/api/appointment/getAllAppointmentsOfUserWithStatus", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllAppointmentsOfUserWithStatus(@RequestParam int page, @RequestParam int size,
			@RequestParam long userID, @RequestParam int statusID) {
		try {
			User user = userRepository.findById(userID).orElse(null);
			if (user != null) {
				if (statusID >= 0 && statusID <= 4) {
					if (statusID == 0) {
						Page<Appointment> appointments = appointmentRepository
								.findByClientAndStatusOrderByAppointmentDateToStringDesc(PageRequest.of(page, size),
										user, Constants.APPOINTMENT_STATUS_PENDING);
						return new ResponseEntity<Object>(appointments, HttpStatus.OK);
					} else if (statusID == 1) {
						Page<Appointment> appointments = appointmentRepository
								.findByClientAndStatusOrderByAppointmentDateToStringDesc(PageRequest.of(page, size),
										user, Constants.APPOINTMENT_STATUS_CONFIRMED);
						return new ResponseEntity<Object>(appointments, HttpStatus.OK);
					} else if (statusID == 2) {
						Page<Appointment> appointments = appointmentRepository
								.findByClientAndStatusOrderByAppointmentDateToStringDesc(PageRequest.of(page, size),
										user, Constants.APPOINTMENT_STATUS_COMPLETED);
						return new ResponseEntity<Object>(appointments, HttpStatus.OK);
					} else {
						Page<Appointment> appointments = appointmentRepository
								.findByClientAndStatusOrderByAppointmentDateToStringDesc(PageRequest.of(page, size),
										user, Constants.APPOINTMENT_STATUS_CANCELED);
						return new ResponseEntity<Object>(appointments, HttpStatus.OK);
					}
				}
				return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);

			} else {
				return new ResponseEntity<Object>(
						new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
								Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND),
						HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getAllAppointmentsWithStatus")
	@RequestMapping(value = "/api/appointment/getAllAppointmentsWithStatus", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllAppointmentsWithStatus(@RequestParam int statusID, @RequestParam int page,
			@RequestParam int size) {
		try {
			if (statusID == 0 || statusID == 1 || statusID == 2 || statusID == 3) {
				if (statusID == 0) {
					Page<Appointment> appointments = appointmentRepository
							.findByStatusOrderByAppointmentDateToStringDesc(PageRequest.of(page, size),
									Constants.APPOINTMENT_STATUS_PENDING);
					return new ResponseEntity<Object>(appointments, HttpStatus.OK);
				} else if (statusID == 1) {
					Page<Appointment> appointments = appointmentRepository
							.findByStatusOrderByAppointmentDateToStringDesc(PageRequest.of(page, size),
									Constants.APPOINTMENT_STATUS_CONFIRMED);
					return new ResponseEntity<Object>(appointments, HttpStatus.OK);
				} else if (statusID == 2) {
					Page<Appointment> appointments = appointmentRepository
							.findByStatusOrderByAppointmentDateToStringDesc(PageRequest.of(page, size),
									Constants.APPOINTMENT_STATUS_COMPLETED);
					return new ResponseEntity<Object>(appointments, HttpStatus.OK);
				} else {
					Page<Appointment> appointments = appointmentRepository
							.findByStatusOrderByAppointmentDateToStringDesc(PageRequest.of(page, size),
									Constants.APPOINTMENT_STATUS_CANCELED);
					return new ResponseEntity<Object>(appointments, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "updateAppointmentStatus")
	@RequestMapping(value = "/api/appointment/updateAppointmentStatus", method = RequestMethod.POST)
	public ResponseEntity<Object> updateAppointmentStatus(@RequestParam long appointmentID,
			@RequestParam int statusID) {
		try {
			Appointment appointment = appointmentRepository.findById(appointmentID).orElse(null);
			if (appointment != null) {
				if (statusID == 1 || statusID == 2 || statusID == 3) {
					appointment = appointmentService.updateStatus(appointment, statusID);
					if (statusID == 2) {
						User client = appointment.getClient();
						client.setAppointmentsCompleted(client.getAppointmentsCompleted() + 1);
						client.setLastUpdate(new Date());
						userRepository.save(client);
					}
					if (statusID == 3) {
						User client = appointment.getClient();
						client.setAppointmentsCanceled(client.getAppointmentsCanceled() + 1);
						client.setLastUpdate(new Date());
						userRepository.save(client);
					}
					return new ResponseEntity<Object>(appointment, HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
				}
			} else {
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "updateClientFile")
	@RequestMapping(value = "/api/appointment/updateClientFile", method = RequestMethod.POST)
	public ResponseEntity<Object> updateClientFile(@RequestParam long appointmentID, @RequestParam String clientFile) {
		try {
			Appointment appointment = appointmentRepository.findById(appointmentID).orElse(null);
			if (appointment != null) {
				appointment.setClientFile(clientFile);
				appointment.setLastUpdate(new Date());
				appointmentRepository.save(appointment);
				return new ResponseEntity<Object>(appointment, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "editClientFile")
	@RequestMapping(value = "/api/appointment/editClientFile", method = RequestMethod.GET)
	public ResponseEntity<Object> editClientFile(@RequestParam long appointmentID, @RequestParam String clientFile) {
		try {
			Appointment appointment = appointmentRepository.findById(appointmentID).orElse(null);
			if (appointment != null) {
				appointment.setClientFile(clientFile);
				appointment.setLastUpdate(new Date());
				appointmentRepository.save(appointment);
				return new ResponseEntity<Object>(appointment, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "checkAvailability")
	@RequestMapping(value = "/api/appointment/checkAvailability", method = RequestMethod.POST)
	public ResponseEntity<Object> checkAvailability(@RequestParam String desiredDate,
			@RequestParam String desiredService) {
		try {
			// INVATA SA PUI CODURI DE PRODUSE DIN CONSTANTE

//			String date = new SimpleDateFormat("yyyy-MM-dd").format(desiredDate);
//			if (desiredService.equals("tuns_barbat")) {
//				List<String> intervals = new ArrayList<String>();
//				List<Appointment> appointments = appointmentRepository.findByAppointmentDateToStringAndValid(desiredDate, true);
//				Appointment app = appointments.get(0);
//				for(Appointment a : appointments) {
//					if(a.equals(app)) {}
//					else {
//						long duration =  a.getAppointmentStartTime().getTime() - app.getAppointmentEndTime().getTime();
//						System.out.println("Durata intre programari: " + duration);
//						if(duration >= 1800000) {
//							String startHour = app.getAppointmentInterval().substring(8,10);
//							String startMinute = app.getAppointmentInterval().substring(11,13);
//							String myTime = startHour + ":" + startMinute;
//							System.out.println("START: " + myTime);
//						}
//					}
//					app = a;
//				}
//			} else if (desiredService == "tuns_femeie") {
//
//			} else if (desiredService == "tuns_copil") {
//
//			} else if (desiredService == "spalat_barbat") {
//
//			} else if (desiredService == "spalat_femeie") {
//
//			} else if (desiredService == "vopsit_radacina") {
//
//			} else if (desiredService == "vopsit_uniform") {
//
//			} else if (desiredService == "balayage") {
//
//			} else if (desiredService == "corectare_culoare") {
//
//			} else if (desiredService == "schimbare_culoare") {
//
//			}
			List<Appointment> free = new ArrayList<Appointment>();
			if (desiredService.equals("tuns_barbat")) {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 10);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);

				Date openTime = cal.getTime();

				cal = Calendar.getInstance();
				cal.set(Calendar.HOUR_OF_DAY, 18);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);

				Date closeTime = cal.getTime();
				List<Appointment> appointments = appointmentRepository
						.findByAppointmentDateToStringAndValid(desiredDate, true);
				Collections.sort(appointments);
				if (appointments.size() == 0) {
					free.addAll(getFreeAppointment(openTime, closeTime, Constants.SERVICE_TUNS_BARBAT));
				} else {
					free.addAll(getFreeAppointment(openTime, appointments.get(0).getAppointmentStartTime(),
							Constants.SERVICE_TUNS_BARBAT));
				}
				for (int i = 0; i < appointments.size(); i++) {
					Appointment app = appointments.get(i);
					Appointment nextApp = null;
					if (i + 1 < appointments.size()) {
						nextApp = appointments.get(i + 1);
					}
					if (nextApp != null) {
						free.addAll(getFreeAppointment(app.getAppointmentEndTime(), nextApp.getAppointmentStartTime(),
								Constants.SERVICE_TUNS_BARBAT));
					} else {
						free.addAll(getFreeAppointment(app.getAppointmentEndTime(), closeTime,
								Constants.SERVICE_TUNS_BARBAT));
					}

				}
			}
			return new ResponseEntity<Object>(free, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	public List<Appointment> getFreeAppointment(Date start, Date end, int duration) {
		List<Appointment> free = new ArrayList<Appointment>();
		Calendar cal = Calendar.getInstance();
		Date openTime = start;
		Date closeTime = end;
		long diff;
		long diffMinutes;
		diff = closeTime.getTime() - openTime.getTime();
		diffMinutes = diff / (60 * 1000);
		Appointment a = null;
		for (int i = 0; i < diffMinutes / duration; i++) {
			a = new Appointment();
			cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 10 + i * duration);
			a.setAppointmentStartTime(cal.getTime());
			cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 10 + (i + 1) * duration);
			a.setAppointmentEndTime(cal.getTime());
			free.add(a);
		}
		return free;
	}

}
