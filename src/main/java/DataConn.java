import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class DataConn implements Runnable {

    private FTPComs ftpComs = new FTPComs();
    //private final int comDataPort = 20;
    private InetAddress ip;
    private BufferedReader inFromServer;

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


        try (Socket serverSocket = makeConn()) //Socket serverSocket = new ServerSocket(comDataPort).accept();
        {
            inFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            //Thread.sleep(5000);
            makeFile("test.txt");
            Thread.sleep(5000);
            makeFile("Effective_C++_errata.txt");
            System.out.println("Sentence above last sentence");

            inFromServer.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeFile(String fileName) throws IOException
    {
        String path = "..\\FTPClient\\downloaded_files\\"+fileName;
        File file = new File(path);
        System.out.println();
        if(file.createNewFile()){
            System.out.println("File created");
        }else System.out.println("File already exists");

        FileWriter writer = new FileWriter(file);

        //StringBuilder sb = new StringBuilder();
        String s = inFromServer.readLine();

        System.out.println("Reading from "+fileName+":");
        if (!inFromServer.ready()){
            System.out.println("ERROR inFromServer not ready");
        }
        while (s != null) {
        //while (inFromServer.ready()) {
            System.out.println("Text: "+s);
            //sb.append(s+"\n");
            writer.write(s+"\n");
            s = inFromServer.readLine();
        }
        System.out.println();

        writer.close();
    }


    private Socket makeConn() throws IOException {

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
