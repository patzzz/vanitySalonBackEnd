package ro.patzzcode.appointmentPlatform.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ro.patzzcode.appointmentPlatform.bo.User;
import ro.patzzcode.appointmentPlatform.repositories.UserRepository;
import ro.patzzcode.appointmentPlatform.utils.PasswordUtils;


@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Value("${token.pass.salt}")
	private String safeSalt;
	
	public User createUser(User user) {
		user.setId(null);
		user.setCreationDate(new Date());
		user.setLastUpdate(new Date());
		user.setPassword(PasswordUtils.generateSecurePassword(user.getPassword(), safeSalt));
		user.setValid(true);
		user.setUsername(user.getMail());
		userRepository.save(user);
		return user;
	}
}
