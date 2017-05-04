package challengeTask.bootstrappingServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Random;

import net.tomp2p.connection.Bindings;
import net.tomp2p.dht.PeerBuilderDHT;
import net.tomp2p.dht.PeerDHT;
import net.tomp2p.p2p.PeerBuilder;
import net.tomp2p.peers.Number160;
import net.tomp2p.peers.PeerAddress;

public class BootstrappingServer {
	
	private ServerSocket socket;
	private PeerDHT bootstrappingPeer;
	private Random random;

	public BootstrappingServer() throws IOException {
		/* InetAddress.getByName(null) returns localhost */
		this.socket = new ServerSocket(0, 0, InetAddress.getByName(null));
		this.random = new Random(85L);
	}
	
	public void start() {
		/* Retrieve inet address and port from ServerSocket, so they are open for sure */
		InetAddress address = socket.getInetAddress();
		int port = socket.getLocalPort();
		System.out.println("Attempting to start peer at " + address.getHostAddress() + ":" + port);
		Bindings bindings = new Bindings().addAddress(address);
		try {
			socket.close();
			bootstrappingPeer = new PeerBuilderDHT(new PeerBuilder(new Number160(random)).bindings(bindings).ports(port).start()).start();
			System.out.println("Bootstrapping peer started!");
			System.out.println("IP: " + address.getHostAddress());
			System.out.println("PORT: " + port);
		} catch (IOException ie) {
			System.err.println("Failed to start bootstrapping peer!");
			System.err.println(ie.getMessage());
		}
	}
	
	public PeerAddress getAddress() {
		return bootstrappingPeer.peerAddress();
	}
	
	public void shutdown() {
		bootstrappingPeer.shutdown();
	}
	
}
