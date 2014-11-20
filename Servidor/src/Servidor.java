import java.io.*;
import java.net.DatagramPacket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Servidor {
    // Choose a port outside of the range 1-1024:
    public static final int PORT = 12345;

    public static void main(String[] args) throws IOException {

        User test1 = create_User(PORT);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        while (true) {
//            String str = test1.takeAnswer();
//            if (str.equals("END")) break;
//            System.out.println("Echoing: " + str);
//            test1.AddCommand(str);
//        }

        String str = test1.takeAnswer();
        System.out.println("Se recibió: " + str);
        Process process = GSTreamer_listen();

        test1.AddCommand("Estoy ready");
        try {
            process.waitFor();
        } catch (InterruptedException e) {
           e.printStackTrace();
        }

    }

    static User create_User(int PORT) throws IOException{

        User user = new User(new ServerSocket(PORT));

        Connect connection = new Connect(user);
        Thread T1 = new Thread(connection);
        T1.start();

        return user;
    }

    static Process GSTreamer_listen(){
        Runtime runTime= Runtime.getRuntime();
        Process process=null;
        try {
            process = runTime.exec("gst-launch-0.10 tcpserversrc host=localhost port=3000 ! decodebin ! audioconvert ! alsasink");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return process;
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
        //                user.Close();
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