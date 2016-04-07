import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by otk_prog on 11.02.2016.
 */
public class GetClients extends Thread {
    @Override
    public void run() {

        try {
            ServerSocket clientsSocket = new ServerSocket(4111);

            while (true) {

                Socket socket = clientsSocket.accept();

                //  InetAddress address = socket.getInetAddress();

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String line = reader.readLine();


                System.out.println(line);

                reader.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
