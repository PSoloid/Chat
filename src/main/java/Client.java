import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        //new GetClients().getClients();

        Thread threadGetClients = new GetClients();
        threadGetClients.start();

        new Client().start();


    }

    public void start() throws IOException {

        send("Connected");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            send(line);
        }
    }


    public void send(String line) throws IOException {
        Socket socket = new Socket("10.0.3.178", 3111);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.println(line);

        writer.flush();

        writer.close();
        socket.close();
    }





}

