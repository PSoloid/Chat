import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        //new GetClients().getClients();

        Thread threadGetClients = new GetClients();
        threadGetClients.start();

        new Client().start();


    }

    public void start() throws IOException {



        Scanner scanner = new Scanner(System.in);

        System.out.println("Input your name");
        String name = scanner.nextLine();

        String state = "online";
        createMessage(name, state, "Connected");

        while (true)  {
            String text = scanner.nextLine();

            if (text.contains("state: ")) {
                state = text.replace("state: ", "");
            }

            createMessage(name, state, text);

        }
    }

    public void createMessage (String name, String state, String text){
    //    Scanner scanner = new Scanner(System.in);

//        System.out.println("Input your name");
//        String name = scanner.nextLine();

      //  Socket socket = new Socket();
        InetAddress address = null;//socket.getInetAddress();
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
//        String curStringDate = new SimpleDateFormat("dd.MM.yyyy").format(curTime);

//        if (text.contains("state: ")) {
//            String state = text.replace("state: ", "");
//        }


        Message mess = new Message(name, address, curDate, state, text);

        try {
            send(mess);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    public void send(Message mess) throws IOException {
        Socket socket = new Socket("192.168.1.154", 3111);
      //  PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        ObjectOutputStream serializer = new ObjectOutputStream(socket.getOutputStream());

        serializer.writeObject(mess);

        serializer.flush();

        serializer.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}

