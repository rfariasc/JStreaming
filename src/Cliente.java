import java.io.*;
import java.net.InetAddress;
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
}
