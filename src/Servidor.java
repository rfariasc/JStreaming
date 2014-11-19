


public class Servidor {

    public static void main(String args[]){






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