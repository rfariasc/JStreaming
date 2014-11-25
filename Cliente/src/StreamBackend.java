import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public final class StreamBackend {

	public StreamBackend() {

	}

	public static void initStreaming(int opt, String ip, String port,
			String source, BoolObj isStop) {

		Process streamProcess = null;
		String command = "ls";

		switch (opt) {
		case 0:
			System.out.println("audio streaming");
			command = "gst-launch-0.10 filesrc location=" + source
					+ " ! tcpclientsink host=" + ip + " port=" + port;
			break;

		case 1:
			System.out.println("video streaming");
			command = "cvlc -vvv " + source + " :sout=#udp{dst=" + ip + ":"
					+ port + "} :sout-keep";
			break;

		default:
			System.out.println("Error in Streaming option");
			break;
		}
		System.out.println(command);
		try {
			streamProcess = GSTreamer_send(ip, port, source, command);
			StreamGobbler errorGobbler = new StreamGobbler(
					streamProcess.getErrorStream(), false, isStop);
			StreamGobbler outputGobbler = new StreamGobbler(
					streamProcess.getInputStream(), false, isStop);
			errorGobbler.start();
			outputGobbler.start();

			KillProcessThread killProcess = new KillProcessThread(
					streamProcess, isStop);
			killProcess.start();
			System.out.println("wait for");
			streamProcess.waitFor();
			System.out.print("listo");

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

	}

	static Process GSTreamer_send(String ip, String port, String source,
			String command) throws IOException {
		Runtime runTime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runTime.exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return process;
	}
	
	static User create_User(int PORT, String hostname) throws IOException {

		InetAddress addr = InetAddress.getByName(hostname);

		System.out.println("addr = " + addr);

		User user = new User(new Socket(addr, PORT));
		//
		// Connect connection = new Connect(user);
		// Thread T1 = new Thread(connection);
		// T1.start();

		Transmitter TX = new Transmitter(user);
		user.setTransmitter(new Thread(TX));
		user.Transmitter.start();

		Receiver RX = new Receiver(user);
		user.setReceptor(new Thread(RX));
		user.Receptor.start();

		return user;
	}
}
