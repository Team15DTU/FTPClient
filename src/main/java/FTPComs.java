import com.sun.xml.internal.bind.v2.runtime.output.SAXOutput;

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
		DataOutputStream outToServer;
		BufferedReader inFromServer;
		
		try (Socket socket = new Socket(ip ,comPort))
		{
			/////////////////////////////////////////
			//////////// Create Streams /////////////
			/////////////////////////////////////////
			outToServer = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			inFromServer = new BufferedReader( new InputStreamReader(socket.getInputStream()) );
			/////////////////////////////////////////
			
			
			
			outToServer.close();
			inFromServer.close();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
