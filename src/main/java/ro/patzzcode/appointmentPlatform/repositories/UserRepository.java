package ro.patzzcode.appointmentPlatform.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import ro.patzzcode.appointmentPlatform.bo.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	Optional<User> findByMail(String mail);
	List<User> findByValid(boolean valid);
	List<User> findByBlacklistCount(int blacklistCount);
	List<User> findByFirstNameContaining(String firstName);
	List<User> findByLastNameContaining(String lastName);
}
