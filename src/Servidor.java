import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    // Choose a port outside of the range 1-1024:
    public static final int PORT = 12345;
    public static void main(String[] args)
            throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Started: " + s);
        try {
            // Blocks until a connection occurs:
            Socket socket = s.accept();
            try {
                System.out.println(
                        "Connection accepted: "+ socket);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));
                // Output is automatically flushed
                // by PrintWriter:
                PrintWriter out =
                        new PrintWriter(
                                new BufferedWriter(
                                        new OutputStreamWriter(
                                                socket.getOutputStream())),true);
                while (true) {
                    String str = in.readLine();
                    if (str.equals("END")) break;
                    System.out.println("Echoing: " + str);
                    out.println(str);
                }
                // Always close the two sockets...
            } finally {
                System.out.println("closing...");
                socket.close();
            }
        } finally {
            s.close();
        }
    }

}





//class Receiver implements Runnable {
//
//    int MTU = Global.MTU;
//    private LinkedBlockingQueue<DatagramPacket> received_packages;
//    private LinkedBlockingQueue<Long> delay;
//    private Host comunicante;
//
//    Receiver(LinkedBlockingQueue<DatagramPacket> received_packages, LinkedBlockingQueue<Long> delay, Host comunicante){
//        this.received_packages = received_packages;
//        this.delay = delay;
//        this.comunicante = comunicante;
//    }
//
//
//    @Override
//    public void run() {
//
//        //al primer mensaje recibido se completa toda la info;
//        byte[] comunicante_data = new byte[MTU];
//        DatagramPacket recieved_data = new DatagramPacket(comunicante_data, comunicante_data.length);
//        try {
//            comunicante.getSocket().receive(recieved_data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        delay.add(new Long(System.nanoTime()));
//
//        comunicante.setIPAddress(recieved_data.getAddress());
//        comunicante.setPort(recieved_data.getPort());
//
//        received_packages.add(recieved_data);
//
//        while(true){
//
//            byte[] recievedData = new byte[MTU];
//            DatagramPacket recievedPacket = new DatagramPacket(recievedData, recievedData.length);
//            try {
//                comunicante.getSocket().receive(recievedPacket);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            delay.add(new Long(System.nanoTime()));
//            System.out.println("lo reibido fue: " + new String(recievedPacket.getData()) + "\nPor parte de: " + recievedPacket.getAddress() + ":" + recievedPacket.getPort() + "\n================");
//            received_packages.add(recievedPacket);
//        }
//    }
//}