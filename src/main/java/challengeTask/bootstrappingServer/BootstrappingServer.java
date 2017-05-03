package challengeTask.bootstrappingServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Random;

import net.tomp2p.connection.Bindings;
import net.tomp2p.p2p.Peer;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;

public class BootstrappingServer {
	
	private ServerSocket socket;
	private Peer server;
	private Random random;

	public BootstrappingServer() throws IOException {
		/* InetAddress.getByName(null) returns localhost */
		this.socket = new ServerSocket(0, 0, InetAddress.getByName(null));
		this.random = new Random(85L);
	}
	
	public void start() {
		System.out.println("Attempting to start peer at " + socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort());
		Bindings binding = new Bindings().addAddress(socket.getInetAddress());
		try {
			server = new PeerBuilder(new Number160(random)).bindings(binding).ports(socket.getLocalPort()).start();
			System.out.println("Bootstrapping peer started!");
			System.out.println("IP: " + socket.getInetAddress().getHostAddress());
			System.out.println("PORT: " + socket.getLocalPort());
		} catch (IOException ie) {
			System.err.println("Failed to start bootstrapping peer!");
			System.err.println(ie.getMessage());
		}
	}
	
	public PeerAddress getAddress() {
		return server.peerAddress();
	}
	
	public void shutdown() {
		server.shutdown();
	}
	
}
