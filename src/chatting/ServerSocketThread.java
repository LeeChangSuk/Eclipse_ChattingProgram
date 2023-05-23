package chatting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSocketThread extends Thread {
	Socket socket;
	ChatServer server;
	BufferedReader in;
	PrintWriter out;
	String name, threadName;

	public ServerSocketThread(ChatServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
		threadName= super.getName();
		System.out.println(socket.getInetAddress() + "���� ����.");
		System.out.println("Thread Name: " + threadName);
	}
	public void sendMsg(String str) {
		out.println(str);
	}
	
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
			sendMsg("��ȭ�� �̸��� �Է�");
			name = in.readLine();
			server.broadCasting("[" + name + "] ���� ����.");
			
			while(true) {
				String str_in= in.readLine();
				server.broadCasting("[" + name + "] " + str_in);
			}
			
		} catch (IOException e) {e.printStackTrace();}
		finally {
			try {
				socket.close();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
