package chatting;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientGUI extends JFrame implements ActionListener, Runnable{
	// Ŭ���̾�Ʈ ȭ��
	Container container = getContentPane();
	JTextArea textArea = new JTextArea();
	JScrollPane scrollPane = new JScrollPane(textArea);
	JTextField textField = new JTextField();
	// ��ſ�
	Socket socket;
	PrintWriter out;
	BufferedReader in;
	String str;		// ä�� ���ڿ� ����
	
	public ClientGUI(String ip, int port) {
		// frame ����
		setTitle("Talk");
		setSize(550, 400);
		setLocation(400, 400);
		init();
		start();
		setVisible(true);
		// ��� �ʱ�ȭ
		initNet(ip, port);
		System.out.println("ip = "+ ip);
	}
	
	private void initNet(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	private void init() {
		textArea.setEditable(false);
		container.setLayout(new BorderLayout());
		container.add("Center", scrollPane);
		container.add("South", textField);
	}
	private void start() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		textField.addActionListener(this);
	}
	@Override
	public void run() {
		while(true) {
			try {
				str = in.readLine();
				textArea.append(str + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		str = textField.getText();
		out.println(str);
		textField.setText("");
		
	}
}
