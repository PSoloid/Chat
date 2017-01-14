import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
        send("intro " + name, name);

        String state = "";

        while (true)  {
            String text = scanner.nextLine();

            if (text.contains("state: ")) {
                state = text.replace("state: ", "");
            }

            createMessage(text, state);

        }
    }

    public void createMessage (String text, String state){
        Scanner scanner = new Scanner(System.in);

//        System.out.println("Input your name");
//        String name = scanner.nextLine();

        Socket socket = new Socket();
        InetAddress address = socket.getInetAddress();

        long curTime = System.currentTimeMillis();
        Date curDate = new Date(curTime);
//        String curStringDate = new SimpleDateFormat("dd.MM.yyyy").format(curTime);

//        if (text.contains("state: ")) {
//            String state = text.replace("state: ", "");
//        }


        Message mess = new Message(name, address, curDate, state, text);


        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void send(String text, String name) throws IOException {
        Socket socket = new Socket("192.168.1.154", 3111);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));



        writer.println(mess);

        writer.flush();

        writer.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}

