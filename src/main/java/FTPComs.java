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
	
	public FTPComs(String ip) throws UnknownHostException
	{ this.ip = InetAddress.getByName(ip); }
	
	//endregion
	
	@Override
	public void run()
	{
		DataOutputStream outToServer;
		BufferedReader inFromServer;
		
		try (Socket socket = new Socket(ip ,comPort))
		{
			/////////////////////////////////////////
			//////////// Create Streams /////////////
			/////////////////////////////////////////
			outToServer = new DataOutputStream(socket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			/////////////////////////////////////////
			
			
			System.out.println("Forbi");
			
			outToServer.writeBytes("dir");
			
			Thread.sleep(5000);
			
			System.out.println("wait done");
			
			inFromServer.lines().forEach(System.out::println);
			
			outToServer.close();
			inFromServer.close();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
