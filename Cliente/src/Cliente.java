import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente {
    public static void main(String args[]) throws IOException {

        int PORT = 12345;

        User user = create_User(PORT, "127.0.0.1");

        for (int i = 0; i < 10; i++) {
                user.AddCommand("Howdy" + i);
                String str = user.takeAnswer();
                System.out.println(str);
            }

        user.AddCommand("END");

        user.Close();
    }

    static User create_User(int PORT, String hostname) throws IOException{

        InetAddress addr = InetAddress.getByName("127.0.0.1");

        System.out.println("addr = " + addr);

        User user = new User(new Socket(addr, PORT));
//
//        Connect connection = new Connect(user);
//        Thread T1 = new Thread(connection);
//        T1.start();

        Transmitter TX = new Transmitter(user);
        user.setTransmitter(new Thread(TX));
        user.Transmitter.start();


        Receiver RX = new Receiver(user);
        user.setReceptor(new Thread(RX));
        user.Receptor.start();



        return user;
    }
}

//class Connect implements  Runnable{
//
//    private User user;
//
//    Connect(User user){
//        this.user = user;
//    }
//
//    @Override
//    public void run() {
//
//        try {
//            // Blocks until a connection occurs:
//            user.setSocket(user.s.accept());
//            System.out.println("Connection accepted: " + user.socket);
//
//
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//}

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