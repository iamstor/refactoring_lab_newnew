
import java.io.IOException;
import java.util.Scanner;

public class Client {



    public static void main(String[] args) throws IOException {
        socketConnector client = new socketConnector();
        App app = new App();
        Scanner scanner = new Scanner(System.in);
        app.print_help();
        while (scanner.hasNextLine()){
            String input=scanner.nextLine();
            if  (input.equals("exit"))
                app.handleExit();
            String response = client.sendMessage(input);
            response=response.replace("\t","\n");
            System.out.println(response);
        }
    }
}
