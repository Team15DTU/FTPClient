import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DataConn implements Runnable {

    //region Fields

    private final int comPort = 22;

    //endregion

    //region Constructor


    //endregion


    @Override
    public void run()
    {
        try (Socket serverSocket = new ServerSocket(comPort).accept())
        {

            BufferedReader inFromServer = new BufferedReader(
                    new InputStreamReader(serverSocket.getInputStream()));

            inFromServer.lines().forEach(System.out::println);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}