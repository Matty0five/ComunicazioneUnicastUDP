import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) {
        try {
            int portaServer = 6789;                 // porta del server
            InetAddress IPServer = InetAddress.getByName("localhost");
            boolean connesso = true;
            byte[] bufferIN = new byte[1024];       // buffer spedizione e ricezione
            byte[] bufferOUT = new byte[1024];
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            // creazione del socket
            DatagramSocket clientSocket = new DatagramSocket();
            System.out.println("Client pronto.");

            while(connesso){
                System.out.print("Inserisci una scritta da far mimare: ");

                // preparazione del messaggio da spedire
                String daSpedire = input.readLine();
                bufferOUT = daSpedire.getBytes();

                // trasmissione del dato al server
                DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, IPServer, portaServer);
                clientSocket.send(sendPacket);

                // ricezione del dato dal server
                DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);
                clientSocket.receive(receivePacket);
                String ricevuto = new String(receivePacket.getData());

                // elaborazione dei dati ricevuti
                int numCaratteri = receivePacket.getLength();
                ricevuto = ricevuto.substring(0, numCaratteri); // elimina i caratteri in eccesso
                System.out.println("Dal SERVER: " + ricevuto);

                if(daSpedire.equals("fine")){
                    System.out.println("Mi disconnetto dal server...");
                    connesso = false;
                }
            }

            //termine elaborazione
            clientSocket.close();

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
