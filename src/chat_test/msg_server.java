package chat_test;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;

public class msg_server extends Thread {
	static ArrayList<Socket> users = new ArrayList<Socket>();
	Socket socket;
	String nick="";
	public msg_server(Socket socket) {
		this.socket = socket;
		users.add(socket);
	}

	@Override
	public void run() {
		try {
			while (true) {
				String s;
				InputStream input = socket.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(input,"UTF-8")); // �б�
				if ((s = reader.readLine()) != null) {
					if (s.equals("/quit")) { 
						PrintWriter wr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")),true);
						wr.println(s);
						break;
					}
					if (s.startsWith("/init_name ")) {
						nick=s.split(" ")[1];
						for (int i = 0; i < users.size(); i++) {

							OutputStream out = users.get(i).getOutputStream(); // ����
							PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out,"UTF-8")),true);
							//PrintWriter writer = new PrintWriter(out, true); //
							writer.println(nick+" ���� �����ϼ̽��ϴ�.");
							
							// writer.flush();
						}
						continue;
					}
					
					System.out.println(s);
					for (int i = 0; i < users.size(); i++) {

						OutputStream out = users.get(i).getOutputStream(); // ����
						PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out,"UTF-8")),true);
						//PrintWriter writer = new PrintWriter(out, true); //
						writer.println(s);
						// writer.flush();
					}}

				

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			for (int i = 0; i < users.size(); i++) {

				OutputStream out;
				try {
					out = users.get(i).getOutputStream();
					PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out,"UTF-8")),true);
					//PrintWriter writer = new PrintWriter(out, true); //
					writer.println( nick+"���� ���� �������ϴ�.");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // ����
				
				// writer.flush();
			}
		}
	}

	public static void main(String[] args) {

		
		// TODO Auto-generated method stub
		int socket = 5000;
		try {
			ServerSocket ss = new ServerSocket(socket);
			System.out.println("���� ����");
			while (true) {
				Socket user = ss.accept();
				System.out.println("Ŭ���̾�Ʈ ���� " + user.getLocalAddress() + " : " + user.getLocalPort());
				Thread serverThread = new msg_server(user);
				serverThread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
