package mediscreen.note.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import mediscreen.note.entity.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	public List<Note> findAllByPatId(Integer patId);
}
