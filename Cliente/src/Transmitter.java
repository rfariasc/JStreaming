import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

class Transmitter implements Runnable {
	
	private User user;
	Transmitter(User user) {
		this.user = user;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Transmitter Running");
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(user.socket.getOutputStream())),
					true);
			while (true) {
				String str = user.takeCommand();
				out.println(str);
			}
			// Always close the two sockets...
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}