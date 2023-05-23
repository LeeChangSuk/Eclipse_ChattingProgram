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

	// 프레임 초기화
	protected void initFrame() {
		// 객체 생성
		frame = new JFrame();
		panel = new JPanel();
		label_id = new JLabel("ID: "); label_pw = new JLabel("PW: ");
		textField_id = new JTextField(20); textField_pw = new JTextField(20);
		button_login = new JButton("로그인"); button_signIn = new JButton("회원가입");
		font = new Font("맑은 고딕", Font.BOLD, 18);
		
		frame.setSize(400, 400);
		// 패널 설정
		panel.setLayout(null);
		panel.setBackground(new Color(230, 210, 250));
		// 라벨 설정
		label_id.setLocation(frame.getWidth()/4 - 30, frame.getHeight()/4);
		label_id.setSize(50, 30);
		label_id.setFont(font);
		
		label_pw.setLocation(frame.getWidth()/4 - 40, frame.getHeight()/4 + 40);
		label_pw.setSize(50, 30);
		label_pw.setFont(font);
		// 텍스트 필드 설정
		textField_id.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 7, 200, 25);
		textField_pw.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 47, 200, 25);
		// 버튼 설정
		button_login.setLocation(100, 200);
		button_login.setSize(80, 30);
		button_login.setBackground(new Color(255, 255, 255));
		button_login.addActionListener(this);	// MySQL에서 입력된 계정 유무 확인
		
		button_signIn.setLocation(200, 200);
		button_signIn.setSize(90, 30);
		button_signIn.setBackground(new Color(255, 255, 255));
		button_signIn.addActionListener(e->{
			makeFrame("회원가입");
		});
		// 프레임 구성
		panel.add(label_id); panel.add(label_pw);
		panel.add(textField_id); panel.add(textField_pw); 
		panel.add(button_login); panel.add(button_signIn);
		frame.add(panel);
		frame.setTitle("로그인");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	// 회원가입 창을 따로 생성하기 위한 메소드
	protected void makeFrame(String frameName) {
		JLabel label_id, label_pw, label_nickName;
		JTextField textField_id, textField_pw, textField_nickName;
		JButton button_signIn, button_init;
		
		JFrame newFrame = new JFrame();
		label_id = new JLabel("ID: "); label_pw = new JLabel("PW: "); label_nickName = new JLabel("닉네임: ");
		textField_id = new JTextField(20); textField_pw = new JTextField(20); textField_nickName = new JTextField(20);
		button_signIn = new JButton("회원가입"); button_init = new JButton("초기화");
		Font font = new Font("맑은 고딕", Font.BOLD, 18);

		
		newFrame.setSize(400, 400);
		newFrame.setTitle(frameName);
		newFrame.setLayout(null);
		// 라벨 설정
		label_id.setLocation(frame.getWidth()/4 - 30, frame.getHeight()/4);
		label_id.setSize(50, 30);
		label_id.setFont(font);
		
		label_pw.setLocation(frame.getWidth()/4 - 40, frame.getHeight()/4 + 40);
		label_pw.setSize(50, 30);
		label_pw.setFont(font);
		
		label_nickName.setLocation(frame.getWidth()/4 - 60, frame.getHeight()/4 + 80);
		label_nickName.setSize(70, 30);
		label_nickName.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		// 텍스트 필드 설정
		textField_id.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 7, 200, 25);
		textField_pw.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 47, 200, 25);
		textField_nickName.setBounds(frame.getWidth()/4, frame.getHeight()/4 + 87, 200, 25);
		// 버튼 설정
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
	
	// MySQL과 연동을 위한 함수
	protected static Connection makeConnection() {
		String url = "jdbc:mysql://localhost:3306/client_db?characterEncoding=UTF-8 & serverTimezone=UTC";
		String user = "root";
		String password = "soSQL01#";
		Connection connection_DB = null;
		
		// 드라이버 적재 및 DB 연결
		// JavaSE-4 이후론 사용하지 않아도 자동으로 적재되지만 확실히 하기 위해 추가
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 적재 성공");
			connection_DB = DriverManager.getConnection(url, user, password);
			System.out.println("DB 연결 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없습니다.");
			//e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("DB 연결에 실패하였습니다.");
			System.out.println("db가 생성되어 있는지 확인하세요.");
			//e.printStackTrace();
		}
		// Connection 인터페이스 반환
		return connection_DB;
	}
	
	// 사용자의 계정이 존재하는 계정인지 체크, 로그인 할 때 이벤트 발생
	protected void checkAccount() {
		JOptionPane warning = new JOptionPane();	// 팝업창
		String id = textField_id.getText();
		String pw = textField_pw.getText();
		int check_success = 0;		// 0: 계정 존재 x, 1: 계정 존재
		
		if(id.isEmpty() && pw.isEmpty()) {
			warning.showMessageDialog(null, "ID와 PW를 다시 확인하세요.");
			return;
		}
		
		Connection conn = DB_Connection.makeConnection();
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM clients");
			while(rs.next()) {
				if(id.equals(rs.getString("client_id")) && pw.equals(rs.getString("client_pw"))) {
					System.out.println("로그인 성공!");
					frame.dispose();
					check_success = 1;
					break;
				}
			}
			if(check_success == 0) {
				warning.showMessageDialog(null, "존재하지 않는 계정입니다.");
				System.out.println("존재하지 않는 계정입니다.");
				textField_id.requestFocus();
			}
		} catch (SQLException e1) {
			System.out.println("Statement 생성 실패.");
			//e1.printStackTrace();
		}
	}
	
	// 계정 저장, 회원가입 때 이벤트 발생
	protected void saveAccount(String ID, String PW, String NickName) {
		JOptionPane warning = new JOptionPane();	// 팝업창
		String id = ID;
		String pw = PW;
		String nickName = NickName;
		
		if(id.isEmpty() && pw.isEmpty() && nickName.isEmpty()) {
			warning.showMessageDialog(null, "모든 항목을 입력해주세요.");
			return;
		}
		
		Connection conn = DB_Connection.makeConnection();
		try {
			Statement st = conn.createStatement();
			// 문자열 안에 큰 따옴표 "" 가 사용되어 escape 문자가 사용됨.
			// escape 문자란 ' \" '를 의미하고 문자열 안에서 큰 따옴표를 표시할 때 사용
			String query = ("INSERT INTO clients " 
					+ "(client_id, client_pw, client_nickName) "
					+ "VALUES (\"" + id + "\", \"" + pw + "\", \""+ nickName + "\");");
			// 레코드 삭제, 추가, 수정을 위해선 'executeUpdate' 를 사용해야 함.
			// 이외에 'executeQueary' 도 있음
			int result = st.executeUpdate(query);
			if (result == 1) {
				warning.showMessageDialog(null, "회원가입에 성공했습니다.");
				super.dispose();
				System.out.println("레코드 추가 성공");
				System.out.println(query);
			} else {
				System.out.println("레코드 추가 실패");
			}
		} catch (SQLException e) {
			System.out.println("Statement 생성 실패.");
			//e.printStackTrace();
		}
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Connection Close Error!");
			e.printStackTrace();
		}
	}
	
	// MySQL에서 입력된 계정 유무 확인
	public void actionPerformed(ActionEvent e) {
		checkAccount();
	}

	public static void main(String[] args) {
		DB_Connection t = new DB_Connection();
	}
}
