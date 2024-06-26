import java.io.*;
import java.net.*;

class UDPServer{
    public static void main(String[] args) {
        DatagramSocket serverSocket;
        try {
            serverSocket = new DatagramSocket(6789);
            boolean attivo = true;                  // per la ripetizione del servizio
            byte[] bufferIN = new byte[1024];       // buffer spedizione e ricezione
            byte[] bufferOUT = new byte[1024];
            
            System.out.println("SERVER avviato...");
            while(attivo){
                // definizione del datagramma
                DatagramPacket receivePacket = new DatagramPacket(bufferIN, bufferIN.length);

                // attesa della ricezione data dal client
                serverSocket.receive(receivePacket);

                // analisi del pacchetto ricevuto
                String ricevuto = new String(receivePacket.getData());
                int numCaratteri = receivePacket.getLength();
                ricevuto = ricevuto.substring(0, numCaratteri); //elimina i caratteri in eccesso
                System.out.println("RICEVUTO: "+ricevuto);

                // riconoscimento dei parametri del socket del client:
                InetAddress IPClient = receivePacket.getAddress();
                int portaClient = receivePacket.getPort();

                // preparo il dato da spedire
                String daSpedire = ricevuto;
                bufferOUT = daSpedire.getBytes();

                // invio del datagramma
                DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, IPClient, portaClient);
                serverSocket.send(sendPacket);
                
                // controllo termine esecuzione del server
                if(ricevuto.equals("fine")){
                    System.out.println("Server chiuso.");
                    attivo = false;
                }
            }
            serverSocket.close();
        } catch (SocketException ex) {
            ex.printStackTrace();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}