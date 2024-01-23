import java.io.*;
import java.net.*;

public class client1{
    public static void main(String[] args){

        // Produces error if provided arguments are less than required
        if(args.length<2){
            System.out.println("IP Address Format has to be: client1.java <ipaddr> <port>");
            return;
        }

        //Stores IP address and Port No.
        String hostaddress = args[0];
        int portNo = Integer.parseInt(args[1]);

        //Checking if port no is a valid entry
        if(portNo > 65535 || portNo < 0){
            System.out.println("Port Number Invalid");
            return;
        }

        try{

            //Gives IPv4 representation of the provided IP address
            InetAddress address = InetAddress.getByName(hostaddress);
            //Creates a datagram socket to communicate with server
            DatagramSocket socket = new DatagramSocket();

            //Code for reading information from text files
            String filePath = "./messages.txt";
            File file = new File(filePath);

            //Checks whether the provided file is empty. If it is empty it closes the socket and returns
            if(file.length() == 0){
                System.out.println("Provided File is empty");
                socket.close();
                return ;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line;

            //Send line by line datagram packets to server
            while((line = br.readLine()) != null){
                byte[] buffer = line.getBytes();

                DatagramPacket response = new DatagramPacket(buffer, buffer.length, address, portNo);
                socket.send(response);

                Thread.sleep(1000);

            }

            socket.close();
            br.close();
        } catch(SocketTimeoutException ex){
            System.out.println("Timeout error: "+ex.getMessage());
        } catch(IOException ex){
            System.out.println("Client error: "+ex.getMessage());
        }catch(InterruptedException ex){
            System.out.println("Exception raised "+ex.getMessage());
        }
    }
}