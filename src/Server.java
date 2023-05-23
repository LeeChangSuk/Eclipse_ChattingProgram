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
			System.out.println("연결중...");
			ss = new ServerSocket(5000);
			socket = ss.accept();
			socketList.add(socket);
			
			out= new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println("연결 성공.");
			
			out.println("사용자의 이름을 입력해주세요: ");
			out.flush();
			System.out.println("이름 요청중...");
			String userName = in.readLine();
			System.out.println(userName + ": 클라이언트 이름");
			
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
					System.out.println("연결 종료.");
					break;
				}
				
				System.out.println("클라이언트: " + msg);
				System.out.print("보낼 문자열: ");
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
				System.out.println("소켓을 닫는데 실패.");
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		Server s = new Server();

	}
}

