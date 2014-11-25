import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class User {

	ServerSocket s;
	Socket socket;
	Thread Receptor;
	Thread Transmitter;

	LinkedBlockingQueue<String> Commands = new LinkedBlockingQueue<String>();
	LinkedBlockingQueue<String> Answer = new LinkedBlockingQueue<String>();

	User(Socket socket) {
		this.s = null;
		this.socket = socket;
	}

	User(ServerSocket s) {
		this.s = s;
		System.out.println("Started: " + s);
	}

	public void Close() {

		Receptor.stop();
		Transmitter.stop();
		// Receptor.interrupt();
		// Transmitter.interrupt();

		try {
			if (s != null) {
				s.close();
			}
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Commands.clear();
		Commands = null;
		Answer.clear();
		Answer = null;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setReceptor(Thread receptor) {
		Receptor = receptor;
	}

	public void setTransmitter(Thread transmitter) {
		Transmitter = transmitter;
	}

	public String takeAnswer() {
		String str = "no pasa na";
		try {
			str = Answer.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return str;
	}

	public void AddAnswer(String str) {
		Answer.add(str);
	}

	public String takeCommand() {
		String str = "no pasa na";
		try {
			str = Commands.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return str;
	}

	public void AddCommand(String str) {
		Commands.add(str);
	}

}
