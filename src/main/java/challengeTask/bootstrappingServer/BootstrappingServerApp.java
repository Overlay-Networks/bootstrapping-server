package challengeTask.bootstrappingServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BootstrappingServerApp {
	
	public static void main(String[] args) {
		BootstrappingServer server;
		try {
			server = new BootstrappingServer();
			server.start();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String line = "";
			while(!line.equalsIgnoreCase("exit") && !line.equalsIgnoreCase("quit")) {
				line = in.readLine();
			}
			server.shutdown();
		} catch (IOException e) {
			System.err.println("Failed to start bootstrapping server!");
			e.printStackTrace();
		}
	}

}
