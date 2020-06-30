package ro.patzzcode.appointmentPlatform.security.restcontrollers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.patzzcode.appointmentPlatform.bo.User;
import ro.patzzcode.appointmentPlatform.repositories.UserRepository;
import ro.patzzcode.appointmentPlatform.security.bo.LoginUser;
import ro.patzzcode.appointmentPlatform.security.bo.Token;
import ro.patzzcode.appointmentPlatform.security.repositories.TokenRepository;
import ro.patzzcode.appointmentPlatform.utils.PasswordUtils;
import ro.patzzcode.appointmentPlatform.utils.TokenGenerator;


@RestController
public class AuthController {

	@Value("${token.oauth.clientId}")
	private String safeClientID;
	@Value("${token.oauth.clientSecret}")
	private String safeClientSecret;
	@Value("${token.pass.salt}")
	private String safeSalt;

	@Autowired
	private UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	private String wrongCredentials = "{\"Message\":\"wrong credentials\"}";
	private String userDoesNotExist = "{\"Message\":\"user does not exist\"}";
	private String userUnverified = "{\"Message\":\"this account was not verified.\"}";

	@Autowired
	private TokenRepository repository;

	@RequestMapping(value = "/auth/{clientID}/registrationToken", method = RequestMethod.POST)
	public ResponseEntity<Object> getRegistrationToken(@PathVariable String clientID,
			@RequestBody String clientSecret) {
		try {
			if (clientID != null && !clientID.isEmpty() && safeClientID.equals(clientID) && clientSecret != null
					&& !clientSecret.isEmpty() && clientSecret.equals(safeClientSecret)) {
				Token t = new Token();
				t.setClientID(clientID);
				t.setCreationDate(new Date());
				t.setLastUpdate(new Date());
				t.setToken(TokenGenerator.generateToken(clientID));
				t = repository.save(t);
				return new ResponseEntity<Object>(t, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/auth/{clientID}/generateToken", method = RequestMethod.POST)
	public ResponseEntity<Object> generateToken(@PathVariable String clientID, @RequestBody LoginUser user) {
		try {
			if (clientID != null && !clientID.isEmpty() && user != null && user.getUsername() != null
					&& user.getPassword() != null) {
				User loginUser = userRepository.findByUsername(user.getUsername())
						.orElse(null);
				if (loginUser == null) {
					return new ResponseEntity<Object>(this.userDoesNotExist,HttpStatus.BAD_REQUEST);
				}
				if (!PasswordUtils.verifyUserPassword(user.getPassword(), loginUser.getPassword(), safeSalt)) {
					return new ResponseEntity<Object>(this.wrongCredentials,HttpStatus.UNAUTHORIZED);
				}
				Token reuse = repository.findByUser(loginUser).orElse(null);
				if (reuse != null) {
					reuse.setToken(TokenGenerator.generateToken(loginUser.getUsername()));
					reuse.setLastUpdate(new Date());
					reuse = repository.save(reuse);
					if(loginUser.getStatus() == 0 || loginUser.getStatus() == 1) {;
						return new ResponseEntity<Object>(this.userUnverified,HttpStatus.NOT_ACCEPTABLE);
					}
					return new ResponseEntity<Object>(reuse, HttpStatus.OK);
				}
				Token t = new Token();
				t.setUser(loginUser);
				t.setCreationDate(new Date());
				t.setLastUpdate(new Date());
				t.setToken(TokenGenerator.generateToken(loginUser.getUsername()));
				t = repository.save(t);
				if(loginUser.getStatus() == 0 || loginUser.getStatus() == 1) {
					return new ResponseEntity<Object>(this.userUnverified,HttpStatus.NOT_ACCEPTABLE);
				}
				return new ResponseEntity<Object>(t, HttpStatus.OK);
			} else {
				return new ResponseEntity<Object>(this.wrongCredentials, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
