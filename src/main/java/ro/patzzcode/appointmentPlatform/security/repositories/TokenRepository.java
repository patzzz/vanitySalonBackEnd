package ro.patzzcode.appointmentPlatform.security.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ro.patzzcode.appointmentPlatform.bo.User;
import ro.patzzcode.appointmentPlatform.security.bo.Token;



@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
	Optional<Token> findByToken(String token);
	Optional<Token> findByClientID(String clientID);
	Optional<Token> findByUser(User user);
}
