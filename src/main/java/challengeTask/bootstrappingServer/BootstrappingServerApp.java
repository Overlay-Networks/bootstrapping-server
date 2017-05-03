package challengeTask.bootstrappingServer;

import java.io.IOException;

public class BootstrappingServerApp {
	
	public static void main(String[] args) {
		BootstrappingServer server;
		try {
			server = new BootstrappingServer();
			server.start();
		} catch (IOException e) {
			System.err.println("Failed to start bootstrapping server!");
			e.printStackTrace();
		}
	}

}
