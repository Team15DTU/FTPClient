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
    //private BufferedReader inFromServer;

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

        try
        {
            //Thread.sleep(5000);
            makeFile("test.txt");

            Thread.sleep(2000);

            makeFile("README.txt");

            System.out.println("Sentence above last sentence");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeFile(String fileName) throws IOException
    {   Socket serverSocket = makeConn();
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        int bytesRead=0;

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
            System.out.println("ERROR inFromServer in DataConn class is not ready");
        }
        while (s != null) {
            //We only want to print out the first kB of the file
            bytesRead += (s.getBytes().length);
            if (bytesRead<1024){
                System.out.println("Text: "+s);
            }
            //The whole file has to be downloaded
            writer.write(s+"\n");
            s = inFromServer.readLine();
        }
        System.out.println();

        writer.close();
        inFromServer.close();
        serverSocket.close();
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
