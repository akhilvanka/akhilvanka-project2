import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Server{

    private ServerSocket server;
    private ArrayList<LocalDateTime> connectedTimes;

    public Server(int port) throws IOException {
        server = new ServerSocket(port);
        connectedTimes = new ArrayList<LocalDateTime>();
    }

    public void serve(int a) throws IOException {
        for (int i = 0; i < a; i++) {
            new Thread(() -> { 
            try {
                Socket client = server.accept();
                connectedTimes.add(LocalDateTime.now());
                BufferedReader incoming = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter outgoing = new PrintWriter(client.getOutputStream(), true);
                if (!incoming.readLine().equals("12345")) {
                    outgoing.println("couldn't handshake");
                    incoming.close();
                    outgoing.close();
                    client.close();
                    return;
            }
            String input = incoming.readLine();
            outgoing.println(primeFactors(input));
            outgoing.flush();
            incoming.close();
            outgoing.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }}).start();
        }
    }

    private String primeFactors(String n) {
        try {
            int num = Integer.parseInt(n);
            ArrayList<Integer> factors = new ArrayList<Integer>();
            for (int i = 1; i <= num; i++) {
                if (num % i == 0) {
                    factors.add(i);
                }
            }
            return "The number " + n + " has " + factors.size() + " factors";
        } catch (NumberFormatException e) {
            return "There was an exception on the server";
        }
    }

    public ArrayList<LocalDateTime> getConnectedTimes() {
        return connectedTimes;
    }

    public void disconnect() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}