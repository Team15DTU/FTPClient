import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Main {
    private static final int comPort = 21;
    private static InetAddress ip;
    private static String dataAddress;
    private static DataOutputStream outToServer1;
    private static BufferedReader inFromServer1;

    public static void main(String[] args) throws UnknownHostException {
        String hostName = "ftp.cs.brown.edu";
        ip = InetAddress.getByName(hostName);

        try (Socket socket = new Socket(ip, comPort)) {
            outToServer1 = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            inFromServer1 = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //Read welcome message
            Thread.sleep(200);
            while (inFromServer1.ready()) {
                String response = inFromServer1.readLine();
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
            makeFile("test.txt");

            //We have to enter passive mode again since the server closes the data connection
            // after each file transfer and uses a new port.
            dataAddress = message("PASV");
            message("CWD /pub/");
            //message("SIZE README");
            message("RETR README");
            makeFile("README.txt");

            dataAddress = message("PASV");
            message("CWD /incoming/");
            //message("SIZE README");
            message("STOR Upload_test_file.txt");
            uploadFile("Upload_test_file.txt");

            outToServer1.close();
            inFromServer1.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String message(String m) throws IOException, InterruptedException {
        outToServer1.writeBytes(m + "\r\n");
        outToServer1.flush();
        Thread.sleep(200);
        String response = "";
        while (inFromServer1.ready()) {
            response = inFromServer1.readLine();
            System.out.println(response);
        }
        return response;
    }

    private static void uploadFile(String fileName) throws IOException {
        Socket serverSocket = makeConn();
        PrintStream fileToServer = new PrintStream(serverSocket.getOutputStream());
        String path = "..\\FTPClient\\upload_files\\" + fileName;

        BufferedReader reader = new BufferedReader(new FileReader(path));
        String s = reader.readLine();

        System.out.println();
        System.out.println("Uploading " + fileName);
        while (s != null) {
            System.out.println("Uploaded text: " + s);
            fileToServer.println(s);

            s = reader.readLine();
        }
        reader.close();
        fileToServer.close();
        serverSocket.close();
    }

    private static void makeFile(String fileName) throws IOException {
        Socket serverSocket = makeConn();
        BufferedReader fileFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        int bytesRead = 0;

        String path = "..\\FTPClient\\downloaded_files\\" + fileName;

        File file = new File(path);
        System.out.println();
        if (file.createNewFile()) {
            System.out.println("File created");
        } else System.out.println("File already exists");

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        String s = fileFromServer.readLine();

        System.out.println("Reading from " + fileName + ":");
        if (!fileFromServer.ready()) {
            System.out.println("ERROR fileFromServer in DataConn class is not ready");
        }
        while (s != null) {
            //We only want to print out the first kB of the file
            bytesRead += (s.getBytes().length);
            if (bytesRead < 1024) {
                System.out.println("Text: " + s);
            }
            //The whole file has to be downloaded
            writer.write(s + "\n");
            s = fileFromServer.readLine();
        }
        System.out.println();

        writer.close();
        fileFromServer.close();
        serverSocket.close();
    }

    private static Socket makeConn() throws IOException {
        String getPort = dataAddress;

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