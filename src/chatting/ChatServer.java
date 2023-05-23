package chatting;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
	ServerSocket serverSocket;
	Socket socket;
	ArrayList<Thread> list;
	
	public ChatServer() {
		list = new ArrayList<Thread>();
		System.out.println("������ ���۵Ǿ����ϴ�.");
	}
	public void giveAndTake() {
		try {
			serverSocket = new ServerSocket(5000);
			serverSocket.setReuseAddress(true);
			
			while(true) {
				socket = serverSocket.accept();
				ServerSocketThread thread = new ServerSocketThread(this, socket);
				addClient(thread);
				thread.start();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	private synchronized void addClient(ServerSocketThread thread) {
		list.add(thread);
		System.out.println("Client 1�� ����. �� "+ list.size() + "��");
	}
	private synchronized void removeClient(Thread thread) {
		list.remove(thread);
		System.out.println("Client 1�� ����. �� "+ list.size() + "��");
	}
	public synchronized void broadCasting(String str) {
		for(int i=0; i<list.size(); i++) {
			ServerSocketThread thread= (ServerSocketThread)list.get(i);
			thread.sendMsg(str);
		}
	}
}
