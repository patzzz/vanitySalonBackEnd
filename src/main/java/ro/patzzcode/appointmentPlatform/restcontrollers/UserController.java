package ro.patzzcode.appointmentPlatform.restcontrollers;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.ApiOperation;
import ro.patzzcode.appointmentPlatform.bo.User;
import ro.patzzcode.appointmentPlatform.repositories.UserRepository;
import ro.patzzcode.appointmentPlatform.security.bo.LoginUser;
import ro.patzzcode.appointmentPlatform.service.MailService;
import ro.patzzcode.appointmentPlatform.service.UserService;
import ro.patzzcode.appointmentPlatform.utils.Constants;
import ro.patzzcode.appointmentPlatform.utils.FrontEndException;
import ro.patzzcode.appointmentPlatform.utils.Mail;
import ro.patzzcode.appointmentPlatform.utils.PasswordUtils;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MailService mailService;
	
	@Value("${token.pass.salt}")
	private String safeSalt;

	@ApiOperation(value = "registerUser")
	@RequestMapping(value = "/api/user/registerUser", method = RequestMethod.POST)
	public ResponseEntity<Object> registerUser(@RequestBody User user) {
		try {
			if (user != null) {
				User otherUser = userRepository.findByMail(user.getMail()).orElse(null);
				if (otherUser == null) {
					user = userService.createUser(user);
					return new ResponseEntity<Object>(user, HttpStatus.CREATED);
				} else {
					return new ResponseEntity<Object>(
							new FrontEndException(new Date(), Constants.ERROR_STATUS_USED_CREDENTIALS,
									Constants.ERROR_ERROR_USED_CREDENTIALS, Constants.ERROR_MESSAGE_USED_CREDENTIALS), HttpStatus.NOT_ACCEPTABLE);
				}
			}
			return new ResponseEntity<Object>(HttpStatus.NOT_ACCEPTABLE);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "login")
	@RequestMapping(value = "/api/user/login", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestBody LoginUser user) {
		try {
			if (user != null && user.getUsername() != null && user.getPassword() != null) {
				User loginUser = userRepository.findByUsername(user.getUsername()).orElse(null);
				if(loginUser == null) {
					return new ResponseEntity<Object>(
							new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
									Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND), HttpStatus.NOT_ACCEPTABLE);
				}
				if (!PasswordUtils.verifyUserPassword(user.getPassword(), loginUser.getPassword(), safeSalt)) {
					return new ResponseEntity<Object>(
							new FrontEndException(new Date(), Constants.ERROR_STATUS_WRONG_CREDENTIALS,
									Constants.ERROR_ERROR_WRONG_CREDENTIALS, Constants.ERROR_MESSAGE_WRONG_CREDENTIALS), HttpStatus.NOT_ACCEPTABLE);
				}else {
					return new ResponseEntity<Object>(loginUser, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<Object>(
						new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
								Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "getUser")
	@RequestMapping(value = "/api/user/getUser", method = RequestMethod.POST)
	public ResponseEntity<Object> getUser(@RequestParam String username) {
		try {
			User user = userRepository.findByUsername(username).orElse(null);
			if (user != null) {
				return new ResponseEntity<Object>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(
						new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
								Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "getAllUsers")
	@RequestMapping(value = "/api/user/getAllUsers", method = RequestMethod.POST)
	public ResponseEntity<Object> getAllUsers() {
		try {
			List<User> users = userRepository.findByValid(true);
			return new ResponseEntity<Object>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "getAllBlacklistUsers")
	@RequestMapping(value = "/api/user/getAllBlacklistUsers", method = RequestMethod.POST)
	public ResponseEntity<Object> getAllBlacklistUsers() {
		try {
			List<User> users = userRepository.findByBlacklistCount(3);
			return new ResponseEntity<Object>(users, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * This method updates user for whom User u was provided
	 * 
	 * @param token - The token for authentication check
	 * @param u     - The user for whom the update operation is to be performed
	 * @return - The method returns a Object of User type
	 */
	@ApiOperation(value = "updateUser - This method updates user for whom User u was provided")
	@RequestMapping(value = "/api/user/user", method = RequestMethod.PATCH)
	public ResponseEntity<Object> updateUser(@RequestBody User u) {
		try {
//			if (!authService.isTokenValid(token)) {
//				return new ResponseEntity<Object>(
//						new FrontEndException(new Date(), Constants.ERROR_STATUS_UNAUTHORIZED,
//								Constants.ERROR_ERROR_UNAUTHORIZED, Constants.ERROR_MESSAGE_UNAUTHORIZED),
//						HttpStatus.UNAUTHORIZED);
//			}
			if (u != null) {
				u.setLastUpdate(new Date());
				u = userRepository.save(u);
				return new ResponseEntity<Object>(u, HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<Object>(
						new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
								Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * This method generates the OTP for register of the user for whom usrID and
	 * emailOrPhone was provided
	 * 
	 * @param token        - The token for authentication check
	 * @param usrID        - The user id for which OTP is generated
	 * @param emailOrPhone - The email or phone nr used for sending the OTP
	 * @return - This method returns an OK HttpStatus
	 */
	@ApiOperation(value = "generateOTP - This method generates the OTP for register of the user for whom usrID and emailOrPhone was provided")
	@RequestMapping(value = "/api/user/OTP/generateOTP", method = RequestMethod.GET)
	public ResponseEntity<Object> generateOTP(@RequestParam Long usrID, @RequestParam String emailOrPhone) {
		try {
//			if (!authService.isTokenValid(token)) {
//				return new ResponseEntity<Object>(
//						new FrontEndException(new Date(), Constants.ERROR_STATUS_UNAUTHORIZED,
//								Constants.ERROR_ERROR_UNAUTHORIZED, Constants.ERROR_MESSAGE_UNAUTHORIZED),
//						HttpStatus.UNAUTHORIZED);
//			}
			User u = userRepository.findById(usrID).get();
			if (u != null) {
				u.setRegisterCodeDate(new Date());
				u.setRegisterCode(Constants.OTP());
				if (emailOrPhone.contains("@")) {
					Mail mail = new Mail();
					mail.setMailFrom("beautysalonappointments@gmail.com");
					mail.setMailTo(emailOrPhone);
					mail.setMailSubject("VANITY - OTP");
					mail.setMailContent("Your OTP is : " + u.getRegisterCode());
					mailService.sendEmail(mail);
				} else {
					// here must implement SEND SMS
				}
				u.setStatus(Constants.USER_STATUS_WAIT_OTP);
				u = userRepository.save(u);
				return new ResponseEntity<Object>("OTP was generated",HttpStatus.CREATED);
			}else {
				return new ResponseEntity<Object>(
						new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
								Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * This method verifyes the OTP of the user for whom usrID and emailOrPhone was
	 * provided
	 * 
	 * @param token - The token for authentication check
	 * @param usrID - The user id for which OTP is verifyed
	 * @param otp   - The otp for verifying
	 * @return - This method returns an Object of User type.
	 */
	@ApiOperation(value = "verifyOTP - This method verifyes the OTP of the user for whom usrID and emailOrPhone was provided")
	@RequestMapping(value = "/api/user/OTP/verifyOTP", method = RequestMethod.POST)
	public ResponseEntity<Object> verifyOTP(@RequestParam Long usrID, @RequestParam String otp) {
		try {
//			if (!authService.isTokenValid(token)) {
//				return new ResponseEntity<Object>(
//						new FrontEndException(new Date(), Constants.ERROR_STATUS_UNAUTHORIZED,
//								Constants.ERROR_ERROR_UNAUTHORIZED, Constants.ERROR_MESSAGE_UNAUTHORIZED),
//						HttpStatus.UNAUTHORIZED);
//			}
			User u = userRepository.findById(usrID).get();
			if (u != null) {
				if (!u.getRegisterCode().equalsIgnoreCase(otp)) {
					return new ResponseEntity<Object>(new FrontEndException(new Date(),
							Constants.ERROR_STATUS_OTP_NOT_ACCEPTABLE, Constants.ERROR_ERROR_OTP_NOT_ACCEPTABLE,
							Constants.ERROR_MESSAGE_OTP_NOT_ACCEPTABLE), HttpStatus.NOT_ACCEPTABLE);
				}
				DateTime otpCreationDate = new DateTime(u.getRegisterCodeDate());
				DateTime today = new DateTime(new Date());
				Period p = new Period(otpCreationDate, today);
				int hours = p.getHours();
				if (hours <= Constants.CODE_EXP_HOURS) {
					u.setRegisterCode(null);
					u.setStatus(Constants.USER_STATUS_VERIFIED);
					u = userRepository.save(u);
					return new ResponseEntity<Object>("OTP was verified", HttpStatus.OK);
				} else {
					return new ResponseEntity<Object>(new FrontEndException(new Date(),
							Constants.ERROR_STATUS_OTP_NOT_ACCEPTABLE, Constants.ERROR_ERROR_OTP_NOT_ACCEPTABLE,
							Constants.ERROR_MESSAGE_OTP_NOT_ACCEPTABLE), HttpStatus.NOT_ACCEPTABLE);
				}
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Object>(
				new FrontEndException(new Date(), Constants.ERROR_STATUS_OTP_NOT_ACCEPTABLE,
						Constants.ERROR_ERROR_OTP_NOT_ACCEPTABLE, Constants.ERROR_MESSAGE_OTP_NOT_ACCEPTABLE),
				HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "increaseBlacklistCount")
	@RequestMapping(value = "/api/user/increaseBlacklistCount", method = RequestMethod.POST)
	public ResponseEntity<Object> increaseBlacklistCount(@RequestParam Long userID) {
		try {
			User user = userRepository.findById(userID).orElse(null);
			if (user != null) {
				user.setBlacklistCount(user.getBlacklistCount()+1);
				if(user.getBlacklistCount() == 3) {
					user.setOnBlacklist(true);
				}
				user.setLastUpdate(new Date());
				userRepository.save(user);
				return new ResponseEntity<Object>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(
						new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
								Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "addToBlacklist")
	@RequestMapping(value = "/api/user/addToBlacklist", method = RequestMethod.POST)
	public ResponseEntity<Object> addToBlacklist(@RequestParam Long userID) {
		try {
			User user = userRepository.findById(userID).orElse(null);
			if (user != null) {
				user.setBlacklistCount(3);
				user.setOnBlacklist(true);
				user.setLastUpdate(new Date());
				userRepository.save(user);
				return new ResponseEntity<Object>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(
						new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
								Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "searchUser")
	@RequestMapping(value = "/api/user/searchUser", method = RequestMethod.POST)
	public ResponseEntity<Object> searchUser(@RequestParam String name) {
		try {
			List<User> users = userRepository.findByFirstNameContaining(name);
			if (users.isEmpty()) {
				List<User> users2 = userRepository.findByLastNameContaining(name);
				if(users2.isEmpty()) {
					return new ResponseEntity<Object>(
							new FrontEndException(new Date(), Constants.ERROR_STATUS_USER_NOT_FOUND,
									Constants.ERROR_ERROR_USER_NOT_FOUND, Constants.ERROR_MESSAGE_USER_NOT_FOUND), HttpStatus.NOT_ACCEPTABLE);
				}else {
					return new ResponseEntity<Object>(users, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<Object>(users, HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
	}
}
