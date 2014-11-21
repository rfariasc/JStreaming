import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Servidor {
    // Choose a port outside of the range 1-1024:
    public static final int TCP_PORT = 12345;
    public static final String UDP_PORT = "5000";


    public static void main(String[] args) throws IOException {

        User user = new User(new ServerSocket(TCP_PORT));

        System.out.println("Waiting for Connection: " + InetAddress.getLocalHost().getHostAddress());
                // Blocks until a connection occurs:
                user.setSocket(user.s.accept());
        System.out.println("Connection accepted: " + user.socket);

        Transmitter TX = new Transmitter(user);
        user.setTransmitter(new Thread(TX));
        user.Transmitter.start();


        Receiver RX = new Receiver(user);
        user.setReceptor(new Thread(RX));
        user.Receptor.start();

        //se manda puerto a usar

        String myIP = user.takeAnswer();
        user.AddCommand(UDP_PORT);

        //SE recibe el uso


        String str = user.takeAnswer();
        System.out.println("Se recibió: " + str);


        Process process = null;

        if(str.equals("audio")){
            process = Run_command("gst-launch-0.10 tcpserversrc host=192.168.0.106 port=3000 ! decodebin ! audioconvert ! alsasink");
        }
        if(str.equals("video") | str.equals("webcam")){
            process = Run_command("cvlc udp://@:5000");
        }
//        if(str.equals("webcam")){
//            process = Run_command("gst-launch udpsrc port=1234 ! \"application/x-rtp, payload=127\" ! rtph264depay ! ffdec_h264 ! xvimagesink sync=false ");
//        }

        if (process==null){
            process.destroy();
            System.out.println("Error con el comando");
        }

        user.AddCommand("Estoy ready");

//        JFrame clientGUI = new JFrame();
//        clientGUI.setDefaultCloseOperation(clientGUI.EXIT_ON_CLOSE);
//        clientGUI.setVisible(true);


        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (int i=0; i< 1000; i++)
            System.out.println(in.readLine());


        try {
            process.waitFor();
        } catch (InterruptedException e) {
           e.printStackTrace();
        }





    }

    static Process Run_command(String command){
        Runtime runTime= Runtime.getRuntime();
        Process process=null;
        try {
            process = runTime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return process;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}