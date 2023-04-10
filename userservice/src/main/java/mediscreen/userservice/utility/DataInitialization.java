package mediscreen.userservice.utility;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitialization implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {

		List<String> commands = new ArrayList<String>(4);
		commands.add(
				"curl -d \"family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside St&phone=100-222-3333\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=TestBorderline&given=Test&dob=1945-06-24&sex=M&address=2 High St&phone=200-333-4444\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=TestInDanger&given=Test&dob=2004-06-18&sex=M&address=3 Club Road&phone=300-444-5555\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=TestEarlyOnset&given=Test&dob=2002-06-28&sex=F&address=4 Valley Dr&phone=400-555-6666\" -X POST http://localhost:8081/patient/add");

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
