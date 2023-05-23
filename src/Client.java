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
				System.out.print("보낼 문자열: ");
				msg = sc.nextLine();
				if(msg.equalsIgnoreCase("quit")) {
					System.out.println("연결 종료.");
					break;
				}
				
				out.println(msg);
				out.flush();
				msg=in.readLine();
				System.out.println("서버: " + msg);
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
				out = new PrintWriter(socket.getOutputStream(), true);			// true -> out.flush를 자동화
				System.out.print("보낼 문자열: ");
				msg = sc.nextLine();
				if(msg.equalsIgnoreCase("quit")) {
					out.println("클라이언트 퇴장");
					System.out.println("연결 종료.");
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
				System.out.println("받은 메시지: " + msg);
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
}