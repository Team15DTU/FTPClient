import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class FTPComs implements Runnable {
	
	//region Fields
	
	private final int comPort = 21;
	private InetAddress ip;
	
	//endregion
	
	//region Constructor
	
	public FTPComs(String hostName) throws UnknownHostException
	{ this.ip = InetAddress.getByName(hostName); }
	
	public FTPComs(InetAddress ip)
	{ this.ip = ip; }
	
	//endregion
	
	@Override
	public void run()
	{
		BufferedWriter outToServer;
		BufferedReader inFromServer;
		
		try (Socket socket = new Socket(ip ,comPort))
		{
			/////////////////////////////////////////
			//////////// Create Streams /////////////
			/////////////////////////////////////////
			outToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			/////////////////////////////////////////
			String s;
			while ((s = inFromServer.readLine()) != null)
			{
				System.out.println(s);
			}
			
			System.out.println("hhe");
			
			outToServer.close();
			inFromServer.close();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
