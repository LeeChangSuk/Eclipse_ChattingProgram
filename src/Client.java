import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	Socket socket;
	BufferedReader in;
	PrintWriter out;
	Scanner sc= new Scanner(System.in);
	Send send; Receive receive;
	
	public Client() {
		try {
			socket = new Socket("localhost", 5000);
			out= new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String request_myName, myName;
			request_myName = in.readLine();
			System.out.println(request_myName);
			myName = sc.nextLine();
			out.println(myName);
			out.flush();
			/*
			send = new Send(socket);
			receive = new Receive(socket);
			
			send.start(); receive.start();
			*/
			while(true){
				/*
				System.out.print("���� ���ڿ�: ");
				msg = sc.nextLine();
				if(msg.equalsIgnoreCase("quit")) {
					System.out.println("���� ����.");
					break;
				}
				
				out.println(msg);
				out.flush();
				msg=in.readLine();
				System.out.println("����: " + msg);
				*/
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) {
		Client c = new Client();

	}
}

class Send extends Thread{
	Socket socket;
	String msg;
	Scanner sc = new Scanner(System.in);
	PrintWriter out;
	
	public Send(Socket socket) {
		this.socket= socket;
	}
	public void run() {
		while(true) {
			try {
				out = new PrintWriter(socket.getOutputStream(), true);			// true -> out.flush�� �ڵ�ȭ
				System.out.print("���� ���ڿ�: ");
				msg = sc.nextLine();
				if(msg.equalsIgnoreCase("quit")) {
					out.println("Ŭ���̾�Ʈ ����");
					System.out.println("���� ����.");
					break;
				}
				out.println(msg);
			} catch (IOException e) {
				out.close();
				e.printStackTrace();
			}finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
class Receive extends Thread{
	Socket socket;
	String msg;
	Scanner sc = new Scanner(System.in);
	BufferedReader in;
	
	public Receive(Socket socket) {
		this.socket= socket;
	}
	public void run() {
		while(true) {
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				msg = in.readLine();
				System.out.println("���� �޽���: " + msg);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
}