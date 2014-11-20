import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
    public static void main(String args[]) throws IOException {

        int PORT = 12345;

        InetAddress addr = InetAddress.getByName("127.0.0.1");

        System.out.println("addr = " + addr);

        Socket socket = new Socket(addr, PORT);

        try {
            System.out.println("socket = " + socket);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Output is automatically flushed
            // by PrintWriter:
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);


            for (int i = 0; i < 10; i++) {
                out.println("howdy " + i);
                String str = in.readLine();
                System.out.println(str);
            }
            out.println("END");
        } finally {
            System.out.println("closing...");
            socket.close();
        }
    }

    static User create_User(int PORT) throws IOException{

        User user = new User(new ServerSocket(PORT));

        Connect connection = new Connect(user);
        Thread T1 = new Thread(connection);
        T1.start();

        return user;
    }
}

class Connect implements  Runnable{

    private User user;

    Connect(User user){
        this.user = user;
    }

    @Override
    public void run() {

        try {
            // Blocks until a connection occurs:
            user.setSocket(user.s.accept());
            System.out.println("Connection accepted: " + user.socket);

            Transmitter TX = new Transmitter(user);
            user.setTransmitter(new Thread(TX));
            user.Transmitter.start();


            Receiver RX = new Receiver(user);
            user.setReceptor(new Thread(RX));
            user.Receptor.start();



        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class Receiver implements Runnable {

    private User user;

    Receiver(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        try {
            System.out.println("Reciever Running");

            BufferedReader in = new BufferedReader(new InputStreamReader(user.socket.getInputStream()));
            while (true) {
                String str = null;
                try {
                    //str = in.readLine();
                    str = in.readLine();
                    System.out.println("lo que se va a agregar es: " + str);
                    if(str == null) {
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

class Transmitter implements Runnable {

    private User user;

    Transmitter(User user){
        this.user = user;
    }

    @Override
    public void run() {

        try {
            System.out.println("Transmitter Running");

            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(user.socket.getOutputStream())), true);

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