package db_test;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DB_Connection extends JFrame implements ActionListener{
	JFrame frame;
	JPanel panel;
	JLabel label_id, label_pw;
	JTextField textField_id, textField_pw; 
	JButton button_login, button_signIn;
	Font font;

	public DB_Connection() {
		initFrame();
	}

	// ������ �ʱ�ȭ
	protected void initFrame() {
		// ��ü ����
		frame = new JFrame();
		panel = new JPanel();
		label_id = new JLabel("ID: "); label_pw = new JLabel("PW: ");
		textField_id = new JTextField(20); textField_pw = new JTextField(20);
		button_login = new JButton("�α���"); button_signIn = new JButton("ȸ������");
		font = new Font("���� ���", Font.BOLD, 18);
		
		frame.setSize(400, 400);
		// �г� ����
		panel.setLayout(null);
		panel.setBackground(new Color(230, 210, 250));
		// �� ����
		label_id.setLocation(frame.getWidth()/4 - 30, frame.getHeight()/4);
		label_id.setSize(50, 30);
		label_id.setFont(font);
		
		label_pw.setLocation(frame.getWidth()/4 - 40, frame.getHeight()/4 + 40);
		label_pw.setSize(50, 30);
		label_pw.setFont(font);
		// �ؽ�Ʈ �ʵ� ����
		textField_id.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 7, 200, 25);
		textField_pw.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 47, 200, 25);
		// ��ư ����
		button_login.setLocation(100, 200);
		button_login.setSize(80, 30);
		button_login.setBackground(new Color(255, 255, 255));
		button_login.addActionListener(this);	// MySQL���� �Էµ� ���� ���� Ȯ��
		
		button_signIn.setLocation(200, 200);
		button_signIn.setSize(90, 30);
		button_signIn.setBackground(new Color(255, 255, 255));
		button_signIn.addActionListener(e->{
			makeFrame("ȸ������");
		});
		// ������ ����
		panel.add(label_id); panel.add(label_pw);
		panel.add(textField_id); panel.add(textField_pw); 
		panel.add(button_login); panel.add(button_signIn);
		frame.add(panel);
		frame.setTitle("�α���");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// ȸ������ â�� ���� �����ϱ� ���� �޼ҵ�
	protected void makeFrame(String frameName) {
		JLabel label_id, label_pw, label_nickName;
		JTextField textField_id, textField_pw, textField_nickName;
		JButton button_signIn, button_init;
		
		JFrame newFrame = new JFrame();
		label_id = new JLabel("ID: "); label_pw = new JLabel("PW: "); label_nickName = new JLabel("�г���: ");
		textField_id = new JTextField(20); textField_pw = new JTextField(20); textField_nickName = new JTextField(20);
		button_signIn = new JButton("ȸ������"); button_init = new JButton("�ʱ�ȭ");
		Font font = new Font("���� ���", Font.BOLD, 18);

		
		newFrame.setSize(400, 400);
		newFrame.setTitle(frameName);
		newFrame.setLayout(null);
		// �� ����
		label_id.setLocation(frame.getWidth()/4 - 30, frame.getHeight()/4);
		label_id.setSize(50, 30);
		label_id.setFont(font);
		
		label_pw.setLocation(frame.getWidth()/4 - 40, frame.getHeight()/4 + 40);
		label_pw.setSize(50, 30);
		label_pw.setFont(font);
		
		label_nickName.setLocation(frame.getWidth()/4 - 60, frame.getHeight()/4 + 80);
		label_nickName.setSize(70, 30);
		label_nickName.setFont(new Font("���� ���", Font.BOLD, 16));
		// �ؽ�Ʈ �ʵ� ����
		textField_id.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 7, 200, 25);
		textField_pw.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 47, 200, 25);
		textField_nickName.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 87, 200, 25);
		// ��ư ����
		button_signIn.setLocation(100, 240);
		button_signIn.setSize(90, 30);
		button_signIn.setBackground(new Color(255, 255, 255));
		button_signIn.addActionListener(e->{
			this.saveAccount(textField_id.getText(),textField_pw.getText(),
					textField_nickName.getText());
			newFrame.dispose();
		});
		
		button_init.setLocation(200, 240);
		button_init.setSize(80, 30);
		button_init.setBackground(new Color(255, 255, 255));
		button_init.addActionListener(e->{
			textField_id.setText("");
			textField_pw.setText("");
			textField_nickName.setText("");
		});
		
		newFrame.add(label_id); newFrame.add(label_pw); newFrame.add(label_nickName);
		newFrame.add(textField_id); newFrame.add(textField_pw); newFrame.add(textField_nickName);
		newFrame.add(button_signIn); newFrame.add(button_init);

		newFrame.setVisible(true);
	}
	
	// MySQL�� ������ ���� �Լ�
	protected static Connection makeConnection() {
		String url = "jdbc:mysql://localhost:3306/client_db?characterEncoding=UTF-8 & serverTimezone=UTC";
		String user = "root";
		String password = "soSQL01#";
		Connection connection_DB = null;
		
		// ����̹� ���� �� DB ����
		// JavaSE-4 ���ķ� ������� �ʾƵ� �ڵ����� ��������� Ȯ���� �ϱ� ���� �߰�
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("����̹� ���� ����");
			connection_DB = DriverManager.getConnection(url, user, password);
			System.out.println("DB ���� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("����̹��� ã�� �� �����ϴ�.");
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB ���ῡ �����Ͽ����ϴ�.");
			System.out.println("db�� �����Ǿ� �ִ��� Ȯ���ϼ���.");
			//e.printStackTrace();
		}
		// Connection �������̽� ��ȯ
		return connection_DB;
	}
	
	// ������� ������ �����ϴ� �������� üũ, �α��� �� �� �̺�Ʈ �߻�
	protected void checkAccount() {
		JOptionPane warning = new JOptionPane();	// �˾�â
		String id = textField_id.getText();
		String pw = textField_pw.getText();
		int check_success = 0;		// 0: ���� ���� x, 1: ���� ����
		
		if(id.isEmpty() && pw.isEmpty()) {
			warning.showMessageDialog(null, "ID�� PW�� �ٽ� Ȯ���ϼ���.");
			return;
		}
		
		Connection conn = DB_Connection.makeConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM clients");
			while(rs.next()) {
				if(id.equals(rs.getString("client_id")) && pw.equals(rs.getString("client_pw"))) {
					System.out.println("�α��� ����!");
					frame.dispose();
					check_success = 1;
					break;
				}
			}
			if(check_success == 0) {
				warning.showMessageDialog(null, "�������� �ʴ� �����Դϴ�.");
				System.out.println("�������� �ʴ� �����Դϴ�.");
				textField_id.requestFocus();
			}
		} catch (SQLException e1) {
			System.out.println("Statement ���� ����.");
			//e1.printStackTrace();
		}
	}
	
	// ���� ����, ȸ������ �� �̺�Ʈ �߻�
	protected void saveAccount(String ID, String PW, String NickName) {
		JOptionPane warning = new JOptionPane();	// �˾�â
		String id = ID;
		String pw = PW;
		String nickName = NickName;
		
		if(id.isEmpty() && pw.isEmpty() && nickName.isEmpty()) {
			warning.showMessageDialog(null, "��� �׸��� �Է����ּ���.");
			return;
		}
		
		Connection conn = DB_Connection.makeConnection();
		try {
			Statement st = conn.createStatement();
			// ���ڿ� �ȿ� ū ����ǥ "" �� ���Ǿ� escape ���ڰ� ����.
			// escape ���ڶ� ' \" '�� �ǹ��ϰ� ���ڿ� �ȿ��� ū ����ǥ�� ǥ���� �� ���
			String query = ("INSERT INTO clients " 
					+ "(client_id, client_pw, client_nickName) "
					+ "VALUES (\"" + id + "\", \"" + pw + "\", \""+ nickName + "\");");
			// ���ڵ� ����, �߰�, ������ ���ؼ� 'executeUpdate' �� ����ؾ� ��.
			// �̿ܿ� 'executeQueary' �� ����
			int result = st.executeUpdate(query);
			if (result == 1) {
				warning.showMessageDialog(null, "ȸ�����Կ� �����߽��ϴ�.");
				super.dispose();
				System.out.println("���ڵ� �߰� ����");
				System.out.println(query);
			} else {
				System.out.println("���ڵ� �߰� ����");
			}
		} catch (SQLException e) {
			System.out.println("Statement ���� ����.");
			//e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Connection Close Error!");
			e.printStackTrace();
		}
	}
	
	// MySQL���� �Էµ� ���� ���� Ȯ��
	public void actionPerformed(ActionEvent e) {
		checkAccount();
	}

	public static void main(String[] args) {
		DB_Connection t = new DB_Connection();
	}
}
