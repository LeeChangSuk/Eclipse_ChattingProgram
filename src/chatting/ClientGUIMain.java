package chatting;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClientGUIMain {

	public static void main(String[] args) {
		
		try {
			InetAddress ia;
			ia = InetAddress.getLocalHost();
			String ip_str = ia.toString();
			String ip = ip_str.substring(ip_str.indexOf("/") + 1);
			new ClientGUI(ip, 5000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		

	}

}
