import java.net.UnknownHostException;

public class Main {
	
	public static void main(String[] args) throws UnknownHostException
	{
		FTPComs ftpComs = new FTPComs("ftp.cs.brown.edu");
		
		ftpComs.run();
	}
	
}
