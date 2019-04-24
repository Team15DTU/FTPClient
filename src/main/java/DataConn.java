import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class DataConn implements Runnable {


    //private final int comDataPort = 20;
    private InetAddress ip;

    public DataConn(String hostName) throws UnknownHostException {
        this.ip = InetAddress.getByName(hostName);
    }


    @Override
    public void run() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try (Socket serverSocket = makeConn(); //Socket serverSocket = new ServerSocket(comDataPort).accept();
             BufferedReader inFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()))) {
            System.out.println("Connection established");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Socket makeConn() throws IOException {

        FTPComs ftpComs = new FTPComs();
        String getPort = ftpComs.getDataAddress();

        StringTokenizer st = new StringTokenizer(getPort, "(,)");

        if (st.countTokens() < 7) throw new IOException("You're not logged in");
        //We only need the port number
        for (int i = 0; i < 5; i++) {
            st.nextToken();
        }
        int portNr = 256 * Integer.parseInt(st.nextToken())
                + Integer.parseInt(st.nextToken());
        return new Socket(ip, portNr);
    }
}
