import java.io.*;
import java.net.Socket;

public class FTPComs implements Runnable {
	
	//region Fields
	
	private final int comPort = 21;
	private String ip;
	
	//endregion
	
	//region Constructor
	
	public FTPComs(String ip)
	{ this.ip = ip; }
	
	//endregion
	
	@Override
	public void run()
	{
		DataOutputStream outToServer;
		BufferedReader inFromServer;
		
		try (Socket socket = new Socket(ip, comPort))
		{
			/////////////////////////////////////////
			//////////// Create Streams /////////////
			/////////////////////////////////////////
			outToServer = new DataOutputStream(socket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			/////////////////////////////////////////
			
			System.out.println(inFromServer.readLine());
			
			outToServer.close();
			inFromServer.close();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
