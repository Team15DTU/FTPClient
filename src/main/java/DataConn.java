import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class DataConn implements Runnable {

    private FTPComs ftpComs = new FTPComs();
    //private final int comDataPort = 20;
    private InetAddress ip;
    //private BufferedReader fileFromServer;

    public DataConn(String hostName) throws UnknownHostException {
        this.ip = InetAddress.getByName(hostName);
    }


    @Override
    public void run() {

        try
        {
            Thread.sleep(3000);
            makeFile("test.txt");

            Thread.sleep(3000);

            makeFile("README.txt");

            Thread.sleep(3000);

            uploadFile("Upload_test_file.txt");

            //System.out.println("Sentence above exit code 0");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void uploadFile(String fileName) throws IOException
    {
        Socket serverSocket = makeConn();
        PrintStream fileToServer = new PrintStream(serverSocket.getOutputStream());
        String path = "..\\FTPClient\\upload_files\\"+fileName;

        BufferedReader reader = new BufferedReader(new FileReader(path));
        String s = reader.readLine();

        System.out.println();
        System.out.println("Uploading "+fileName);
        while (s != null) {
            System.out.println("Uploaded text: " + s);
            fileToServer.println(s);

            s = reader.readLine();
        }

        reader.close();
        fileToServer.close();
        serverSocket.close();
    }

    private void makeFile(String fileName) throws IOException
    {
        Socket serverSocket = makeConn();
        BufferedReader fileFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        int bytesRead=0;

        String path = "..\\FTPClient\\downloaded_files\\"+fileName;

        File file = new File(path);
        System.out.println();
        if(file.createNewFile()){
            System.out.println("File created");
        }else System.out.println("File already exists");

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        String s = fileFromServer.readLine();

        System.out.println("Reading from "+fileName+":");
        if (!fileFromServer.ready()){
            System.out.println("ERROR fileFromServer in DataConn class is not ready");
        }
        while (s != null) {
            //We only want to print out the first kB of the file
            bytesRead += (s.getBytes().length);
            if (bytesRead<1024){
                System.out.println("Text: "+s);
            }
            //The whole file has to be downloaded
            writer.write(s+"\n");
            s = fileFromServer.readLine();
        }
        System.out.println();

        writer.close();
        fileFromServer.close();
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
