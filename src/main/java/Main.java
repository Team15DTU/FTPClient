import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        String hostName = "ftp.cs.brown.edu";
        FTPComs ftpComs = new FTPComs(hostName);
        DataConn dataConn = new DataConn(hostName);

        Thread t1 = new Thread(ftpComs);
        Thread t2 = new Thread(dataConn);

        t1.start();
        t2.start();

        /*
        (new Thread(new FTPComs(hostName))).start();
        (new Thread(new DataConn(hostName))).start();
        */
    }

}
//FTPComs ftpComs = new FTPComs(InetAddress.getByAddress(ip));
//FTPComs ftpComs = new FTPComs("ftp.cs.brown.edu");
//DataConn dataConn = new DataConn();
//FTPComs ftpComs = new FTPComs("speedtest.tele2.net");
