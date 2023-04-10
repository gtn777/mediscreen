package mediscreen.noteapi.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import mediscreen.noteapi.entity.Note;

public interface NoteRepository extends MongoRepository<Note, String> {
	public List<Note> findAllByPatId(Integer patId);
}
