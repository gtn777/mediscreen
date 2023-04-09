package mediscreen.noteapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import mediscreen.noteapi.entity.Note;

public interface NoteRepository extends MongoRepository<Note, String> {

	public Optional<Note> findById(String id);

	public List<Note> findByFirstName(String firstName);

	public List<Note> findByLastName(String lastName);

	public List<Note> findByFirstNameAndLastName(String firstName, String lastName);

}
