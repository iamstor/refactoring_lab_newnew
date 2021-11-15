import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static Socket clientSocket; //сокет для общения
    private static ServerSocket server; // серверсокет
    private static BufferedReader in; // поток чтения из сокета
    private static BufferedWriter out; // поток записи в сокет

    public static void main(String[] args) throws IOException {
        App app=new App();
        app.load();
        try {
            server = new ServerSocket(4004); // серверсокет прослушивает порт 4004

            System.out.println("Сервер запущен!");
            clientSocket = server.accept();
            while (true) {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String input = in.readLine();
                System.out.println(input);
                System.out.println("message got, handling");
                if (input==null) continue;
                //String message=
                String response = ServerApp.execute(app,input);
                response=response.replace("\n","\t");
                out.write(response+ "\n");
                out.flush();

            }

        } catch (IOException e) {
            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            e.printStackTrace();
        }




    }

}
