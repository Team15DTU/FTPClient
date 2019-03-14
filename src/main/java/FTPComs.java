import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class FTPComs implements Runnable {

    //region Fields
    private final int comPort = 21;
    private InetAddress ip;

    //endregion

    //region Constructor

    public FTPComs(String hostName) throws UnknownHostException {
        this.ip = InetAddress.getByName(hostName);
    }

    public FTPComs(InetAddress ip) {
        this.ip = ip;
    }

    //endregion

    @Override
    public void run() {
        DataOutputStream outToServer;
        BufferedReader inFromServer;

        try (Socket socket = new Socket(ip, comPort)) {

            outToServer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread.sleep(200);
            while (inFromServer.ready()) {
                String response = inFromServer.readLine();
                System.out.println(response);
            }
            String Login = "USER";
            String DirChoice = "CWD /u/pkp/";
            String ShowDir = "PASV";
            outToServer.writeBytes(Login + "\r\n");
            outToServer.writeBytes(DirChoice + "\r\n");
            outToServer.writeBytes(ShowDir + "\r\n");
            outToServer.flush();

            System.out.println();

            Thread.sleep(400);
            while (inFromServer.ready()) {
                String response = inFromServer.readLine();
                System.out.println(response);
            }
            outToServer.close();
            inFromServer.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
