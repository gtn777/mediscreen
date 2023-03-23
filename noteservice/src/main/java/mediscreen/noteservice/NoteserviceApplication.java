package mediscreen.noteservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mediscreen.noteservice.entity.Note;
import mediscreen.noteservice.repository.NoteRepository;

@SpringBootApplication
public class NoteserviceApplication implements CommandLineRunner {

	@Autowired
	private NoteRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(NoteserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		// save a couple of customers
		repository.save(new Note("contenu de la note", "Alice", "Smith"));
		repository.save(new Note("Texte contenu ", "Bob", "Smith"));

		// fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (Note customer : repository.findAll()) {
			System.out.println(customer);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));

		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Note customer : repository.findByLastName("Smith")) {
			System.out.println(customer);
		}

	}

}
