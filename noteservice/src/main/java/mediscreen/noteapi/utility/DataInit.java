package mediscreen.noteapi.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {

	@Autowired
	MongoTemplate mongoTemplate;

	@Override
	public void run(String... args) throws Exception {
		mongoTemplate.dropCollection("notes");
		mongoTemplate.createCollection("notes");

		List<String> commands = new ArrayList<String>(9);
		commands.add("curl -d \"patId=1&e=Patient: TestNone Practitioner's notes/recommendations:"
				+ " Patient states that they are 'feeling terrific' Weight at or below recommended level\" -X POST http://localhost:8082/patHistory/add");
		commands.add("curl -d \"patId=2&e=Patient: TestBorderline Practitioner's notes/recommendations:"
				+ " Patient states that they are feeling a great deal of stress at work Patient also complains that their hearing seems Abnormal as of late\" -X POST http://localhost:8082/patHistory/add");
		commands.add("curl -d \"patId=2&e=Patient: TestBorderline Practitioner's notes/recommendations:"
				+ " Patient states that they have had a Reaction to medication within last 3 months Patient also complains that their hearing continues to be problematic\" -X POST http://localhost:8082/patHistory/add");
		commands.add("curl -d \"patId=3&e=Patient: TestInDanger Practitioner's notes/recommendations:"
				+ " Patient states that they are short term Smoker \" -X POST http://localhost:8082/patHistory/add");
		commands.add("curl -d \"patId=3&e=Patient: TestInDanger Practitioner's notes/recommendations:"
				+ " Patient states that they quit within last year Patient also complains that of Abnormal breathing spells Lab reports Cholesterol LDL high\" -X POST http://localhost:8082/patHistory/add");
		commands.add("curl -d \"patId=4&e=Patient: TestEarlyOnset Practitioner's notes/recommendations:"
				+ " Patient states that walking up stairs has become difficult Patient also complains that they are having shortness of breath Lab results indicate Antibodies present elevated Reaction to medication\" -X POST http://localhost:8082/patHistory/add ");
		commands.add("curl -d \"patId=4&e=Patient: TestEarlyOnset Practitioner's notes/recommendations:"
				+ " Patient states that they are experiencing back pain when seated for a long time\" -X POST http://localhost:8082/patHistory/add");
		commands.add("curl -d \"patId=4&e=Patient: TestEarlyOnset Practitioner's notes/recommendations:"
				+ " Patient states that they are a short term Smoker Hemoglobin A1C above recommended level\" -X POST http://localhost:8082/patHistory/add");
		commands.add("curl -d \"patId=4&e=Patient: TestEarlyOnset Practitioner's notes/recommendations:"
				+ " Patient states that Body Height, Body Weight, Cholesterol, Dizziness and Reaction\" -X POST http://localhost:8082/patHistory/add");

		for (String cmd : commands) {
			ProcessBuilder processBuilder = new ProcessBuilder();

			processBuilder.command("bash", "-c", cmd);

			Process process = processBuilder.start();

			// blocked :(
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			int exitCode = process.waitFor();
			System.out.println("\nExited with error code : " + exitCode);
		}

	}

}
