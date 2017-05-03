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
		System.out.println("Attempting to start peer at " + socket.getInetAddress().getHostAddress() + ":" + socket.getLocalPort());
		Bindings bindings = new Bindings().addAddress(socket.getInetAddress());
		try {
			bootstrappingPeer = new PeerBuilderDHT(new PeerBuilder(new Number160(random)).bindings(bindings).ports(socket.getLocalPort()).start()).start();
			System.out.println("Bootstrapping peer started!");
			System.out.println("IP: " + socket.getInetAddress().getHostAddress());
			System.out.println("PORT: " + socket.getLocalPort());
			socket.accept();
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
