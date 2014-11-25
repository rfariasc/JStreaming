import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Receiver implements Runnable {

	private User user;

	Receiver(User user) {
		this.user = user;
	}

	@Override
	public void run() {
		try {
			System.out.println("Reciever Running");

			BufferedReader in = new BufferedReader(new InputStreamReader(
					user.socket.getInputStream()));
			while (true) {
				String str = null;
				try {
					// str = in.readLine();
					str = in.readLine();
					System.out.println("lo que se va a agregar es: " + str);
					if (str == null) {
						System.out.println("Connection Closed! =(");
						user.Close();
						break;
					}

					user.Answer.add(str);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}