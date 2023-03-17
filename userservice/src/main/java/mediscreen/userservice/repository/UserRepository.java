package mediscreen.userservice.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import mediscreen.userservice.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	Optional<User> findByFirstNameAndLastName(String firstName, String lastName);

}
