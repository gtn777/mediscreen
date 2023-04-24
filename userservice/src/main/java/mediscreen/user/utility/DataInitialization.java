package mediscreen.user.utility;

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
		commands.add(
				"curl -d \"family=Ferguson&given=Lucas&dob=1968-06-22&sex=M&address=2 Warren Street&phone=387-866-1399\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=Rees&given=Pippa&dob=1952-09-27&sex=F&address=745 West Valley Farms Drive&phone=628-423-0993\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=Arnold&given=Edward&dob=1952-11-11&sex=M&address=599 East Garden Ave&phone=123-727-2779\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=Sharp&given=Anthony&dob=1946-11-26&sex=M&address=894 Hall Street&phone=451-761-8383\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=Ince&given=Wendy&dob=1958-06-29&sex=F&address=4 Southampton Road&phone=802-911-9975\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=Ross&given=Tracey&dob=1949-12-07&sex=F&address=40 Sulphur Springs Dr&phone=131-396-5049\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=Wilson&given=Claire&dob=1966-12-31&sex=F&address=12 Cobblestone St&phone=300-452-1091\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=Buckland&given=Max&dob=1945-06-24&sex=M&address=193 Vale St&phone=833-534-0864\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=Clark&given=Natalie&dob=1964-06-18&sex=F&address=12 Beechwood Road&phone=241-467-9197\" -X POST http://localhost:8081/patient/add");
		commands.add(
				"curl -d \"family=Bailey&given=Piers&dob=1959-06-28&sex=M&address=1202 Bumble Dr&phone=747-815-0557\" -X POST http://localhost:8081/patient/add");

		for (String cmd : commands) {
			ProcessBuilder processBuilder = new ProcessBuilder();

			processBuilder.command("bash", "-c", cmd);

			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
			int exitCode = process.waitFor();
		}
	}
}
