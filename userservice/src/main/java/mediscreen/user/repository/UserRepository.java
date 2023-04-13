package mediscreen.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mediscreen.user.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByFirstNameAndLastName(String firstName, String lastName);

	List<User> findAllByLastName(String lastName);
	
}
