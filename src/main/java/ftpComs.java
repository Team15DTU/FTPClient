import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ftpComs implements Runnable {
	
	//region Fields
	
	private final int comPort = 21;
	private String ip;
	
	//endregion
	
	//region Constructor
	
	public ftpComs(String ip)
	{ this.ip = ip; }
	
	//endregion
	
	@Override
	public void run()
	{
		DataOutputStream outToServer;
		BufferedReader inFromServer;
		
		try (Socket socket = new Socket(ip, comPort))
		{
			outToServer = new DataOutputStream(socket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
