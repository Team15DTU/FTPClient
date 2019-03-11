public class Main {
	
	public static void main(String[] args)
	{
		FTPComs ftpComs = new FTPComs("ftp://ftp.cs.brown.edu");
		
		ftpComs.run();
	}
	
}
