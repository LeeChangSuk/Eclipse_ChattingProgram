import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
	ServerSocket ss;
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	Scanner sc = new Scanner(System.in);
	Send send; Receive receive;
	ArrayList<Socket> socketList;

	public Server() {
		socketList = new ArrayList<>();
		try {
			System.out.println("������...");
			ss = new ServerSocket(5000);
			socket = ss.accept();
			socketList.add(socket);
			
			out= new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("���� ����.");
			
			out.println("������� �̸��� �Է����ּ���: ");
			out.flush();
			System.out.println("�̸� ��û��...");
			String userName = in.readLine();
			System.out.println(userName + ": Ŭ���̾�Ʈ �̸�");
			
			socket = ss.accept();
			socketList.add(socket);
			/*
			send = new Send(socket);
			receive = new Receive(socket);
			
			send.start(); receive.start();
			*/
			while(true) {
				/*
				String msg = in.readLine();
				if(msg.equalsIgnoreCase("quit")) {
					System.out.println("���� ����.");
					break;
				}
				
				System.out.println("Ŭ���̾�Ʈ: " + msg);
				System.out.print("���� ���ڿ�: ");
				String omsg = sc.nextLine();
				out.println(omsg);
				out.flush();
				*/
			}
		} catch (IOException e) {
			e.printStackTrace();	
		} finally {
			try {
				out.close();
				socket.close();
				ss.close();
			} catch (IOException e) {
				System.out.println("������ �ݴµ� ����.");
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		Server s = new Server();

	}
}

