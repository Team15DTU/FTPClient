import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class FTPComs implements Runnable {

    //region Fields
    private final int comPort = 21;
    private InetAddress ip;
    private static String dataAddress;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;
    //endregion

    public String getDataAddress() {
        return dataAddress;
    }

    //region Constructor
    public FTPComs(String hostName) throws UnknownHostException {
        this.ip = InetAddress.getByName(hostName);
    }

    public FTPComs(InetAddress ip) {
        this.ip = ip;
    }

    public FTPComs() {
    }
    //endregion

    @Override
    public void run() {

        try (Socket socket = new Socket(ip, comPort)) {
            outToServer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Read welcome message
            Thread.sleep(200);
            while (inFromServer.ready()) {
                String response = inFromServer.readLine();
                System.out.println(response);
            }

            System.out.println();

            //Send setup messages to server
            message("USER");
            dataAddress = message("PASV");

            //Change selected directory and get a specific file
            message("CWD /u/pkp/");
            //message("SIZE test.txt");
            message("RETR test.txt");

            Thread.sleep(2000);

            dataAddress = message("PASV");
            message("CWD /pub/");
            //message("SIZE README");
            message("RETR README");

            //Thread.sleep(4000);
            //System.out.println("Server connection is lost now, data connection can still be active");
            outToServer.close();
            inFromServer.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }


    public String message(String m) throws IOException, InterruptedException {
        outToServer.writeBytes(m + "\r\n");
        outToServer.flush();
        Thread.sleep(400);
        String response = "";
        while (inFromServer.ready()) {
            response = inFromServer.readLine();
            System.out.println(response);
        }
        return response;
    }
}
