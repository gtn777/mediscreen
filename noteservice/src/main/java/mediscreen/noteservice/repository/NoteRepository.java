package mediscreen.noteservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import mediscreen.noteservice.entity.Note;

public interface NoteRepository extends MongoRepository<Note, String> {

	public Optional<Note> findById(String id);

	public List<Note> findByFirstName(String lastName);

	public List<Note> findByLastName(String lastName);

}
