import org.omg.IOP.Encoding;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Server {

    private Map<InetAddress, String> ipToName = new HashMap<>();

    public static void main(String[] args) throws IOException {
        new Server().start();
    }

    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket(3111);

        while (true) {
            Socket socket = serverSocket.accept();
            InetAddress address = socket.getInetAddress();

//            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//            String line = reader.readLine();

            ObjectInputStream deserializer = new ObjectInputStream(socket.getInputStream());

            Message mess = null;
            try {
                mess = (Message) deserializer.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (mess.getTextMessage().equals("Connected")) {
                sendLast10(address);
            }

            System.out.println(mess.getName() + " (" + mess.getState() + ") : " + mess.getTextMessage());
            System.out.println(mess);


            //intro Name
//            if (line.contains("intro ")) {
//                String name = line.replace("intro ", "");
//                ipToName.put(address, name);
//                line = "Connected";
//            }



// //             else {
// //              ipToName.put(address, null);
// //           }



//            System.out.println(getName(address) + ": " + line);
//
            sendToLog(mess.getName() + " (" + mess.getState() + ") : " + mess.getTextMessage());
            sendToClients(mess.getName() + " (" + mess.getState() + ") : " + mess.getTextMessage());
//
//            reader.close();
            socket.close();
        }
    }

    private String getName(InetAddress address) {
        String name = ipToName.get(address);
        if (name == null) {
            name = address.toString();
        }
        return name;
    }


    public void sendToClients(String line) throws IOException {

        for (Map.Entry<InetAddress, String> ip : ipToName.entrySet()) {

            Socket socket = new Socket(ip.getKey(), 4111);

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.println(line);

            writer.flush();

            writer.close();
            socket.close();
        }
    }

    public void sendToLog(String line) {
        File file = new File("src/main/resources/logs/messages.txt");

        try {
            //проверяем, что если файл не существует то создаем его
            if (!file.exists()) {
                file.createNewFile();
            }

            //FileWriter обеспечит возможности записи в файл
            FileWriter out = new FileWriter(file.getAbsoluteFile(), true);
            try {
                out.write(line + "\n");
                out.flush();
            } finally {

                out.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendLast10(InetAddress address) {
        File file = new File("src/main/resources/logs/messages.txt");
        String line;
        final int K = 10;
        String[] circleText = new String[K];
        int size = 0;

        try {
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            try {
/* читаем файл построчно в круговой массив */
                while ((line = in.readLine()) != null) {
                    circleText[size % K] = line;
                    size++;
                }

/* вычисляем начало кругового массива и его размер */
                int start = size > K ? (size % K) : 0;
                int count = min(K, size);

/* выводим элементы в порядке чтения */
                for (int i = 0; i < count; i++) {

                        Socket socket = new Socket(address, 4111);

                        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                        writer.println(circleText[(start + i) % K]);

                        writer.flush();

                        writer.close();
                        socket.close();
                }


            } finally {
                in.close();
            }
        } catch (IOException e) {
            System.out.println("Fle not find");
            //throw new RuntimeException(e);
        }
    }

    private int min(int k, int size) {
        return size > k ? k : size;

    }
}