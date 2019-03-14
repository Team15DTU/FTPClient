import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DataConn implements Runnable {

    private final int comPort = 20;

    @Override
    public void run()
    {
        try (Socket serverSocket = new ServerSocket(comPort).accept();
             BufferedReader inFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream())))
        {

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}