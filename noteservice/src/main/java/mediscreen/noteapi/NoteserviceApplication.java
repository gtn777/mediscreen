package mediscreen.noteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NoteserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteserviceApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		repository.deleteAll();
//
//		String essai = """
//				Essai de note:
//					indentation.
//				Et retour.
//				""";
//		repository.save(new Note(essai.toString(), "essai.toString()", "Test"));
//		repository.save(new Note(essai, "essai", "Test"));
//
//		
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (Note customer : repository.findAll()) {
//			System.out.println(customer);
//		}
//		System.out.println();
//
//		
//		System.out.println("Customer found with findByFirstName('Alice'):");
//		System.out.println("--------------------------------");
//		System.out.println(repository.findByFirstName("Alice"));
//
//		System.out.println("Customers found with findByLastName('Smith'):");
//		System.out.println("--------------------------------");
//		for (Note customer : repository.findByLastName("Smith")) {
//			System.out.println(customer);
//		}

}
