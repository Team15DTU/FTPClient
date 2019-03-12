import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
	
	public static void main(String[] args) throws UnknownHostException
	{
		byte ip[] = {125, (byte)182, (byte)156, 7}; // Random ftp server
		// FTPComs ftpComs = new FTPComs(InetAddress.getByAddress(ip));
		FTPComs ftpComs = new FTPComs("ftp.cs.brown.edu");
		//FTPComs ftpComs = new FTPComs("speedtest.tele2.net");
		
		ftpComs.run();
	}
	
}
