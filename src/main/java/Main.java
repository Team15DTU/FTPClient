import java.net.UnknownHostException;

public class Main {
	
	public static void main(String[] args) throws UnknownHostException
	{
		(new Thread(new FTPComs("ftp.cs.brown.edu"))).start();
		(new Thread(new DataConn())).start();
	}
	
}
// FTPComs ftpComs = new FTPComs(InetAddress.getByAddress(ip));
//FTPComs ftpComs = new FTPComs("ftp.cs.brown.edu");
//DataConn dataConn = new DataConn();
//FTPComs ftpComs = new FTPComs("speedtest.tele2.net");