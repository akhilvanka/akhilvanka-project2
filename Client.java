import java.io.*;
import java.net.*;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public Socket getSocket() {
        return socket;
    }

    public void handshake() {
        out.println("12345");
        out.flush();
    }

    public void disconnect() throws IOException {
        out.close();
        in.close();
        socket.close();
    }

    public String request(String a) throws IOException {
        out.println(a);
        out.flush();
        return in.readLine();
    }
}