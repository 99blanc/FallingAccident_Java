package haneum;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class LoginGUI extends JFrame implements ActionListener {
	// DB ArrayList 생성
	public ArrayList<List> listOfDB = new ArrayList<>();
	public boolean existDB = false;
	public boolean isAdmin = false;
	public int num = 0;
	JButton register;
	JButton login;
	JTextField logID;
	JPasswordField logPW;
	@SuppressWarnings("static-access")
	public LoginGUI() throws ClassNotFoundException, SQLException {
		SQLServer SQL = new SQLServer();
		Database DB = new Database();
		SQL.downloadSQL();
		listOfDB = DB.getDB();
		logID = new JTextField("");
		logPW = new JPasswordField("");
		register = new JButton("Register");
		login = new JButton("Login");
		setTitle("한이음(노인복지관련) -> 로그인 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		GridLayout grid = new GridLayout(3, 2);
		grid.setVgap(5);
		Container c = getContentPane();
		c.setLayout(grid);
		c.add(new JLabel("ID", JLabel.CENTER));
		c.add(logID);
		c.add(new JLabel("PW", JLabel.CENTER));
		c.add(logPW);
		c.add(register);
		c.add(login);
		// 버튼 이벤트 추가
		register.addActionListener(this);
		login.addActionListener(this);
		// 패널 크기와 GUI 생성
		setSize(400, 200);
		setVisible(true);
	}
	// 버튼 이벤트 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		String checkID = logID.getText();
		String checkPW = new String(logPW.getPassword());
		if(e.getSource() == login) {
			for (int i = 0; i < listOfDB.size(); i++) {
				// 관리자 로그인 확인 조건문
				if (checkID.equals(listOfDB.get(i).getID()) && checkPW.equals(listOfDB.get(i).getPW()) && listOfDB.get(i).getAdmin() == true) {
					existDB = true;
					isAdmin = true;
					num = i;
					break;
				}
				// 사용자 로그인 확인 조건문
				else if (checkID.equals(listOfDB.get(i).getID()) && checkPW.equals(listOfDB.get(i).getPW())) {
					existDB = true;
					num = i;
					break;
				}
			}
			// 로그인 예외 처리 조건문
			if (existDB && isAdmin) {
				new AdminMenuGUI(num);
				setVisible(false);
				System.out.println("Info:성공적으로 관리자 로그인되었습니다.");
			}
			else if (existDB && !isAdmin) {
				new UserMenuGUI(num);
				setVisible(false);
				this.dispose();
				System.out.println("Info:성공적으로 사용자 로그인되었습니다.");
			}
			else if (checkID.length() < 1 || checkPW.length() < 1) {
				JOptionPane.showMessageDialog(null, "ID 혹은 PW를 입력하세요.");
				System.out.println("Error:ID 혹은 PW를 입력하세요.");
			}
			else if (!existDB){
				JOptionPane.showMessageDialog(null, "ID 혹은 PW를 다시 확인하세요.");
				System.out.println("Error:ID 혹은 PW를 다시 확인하세요.");
			}
			// 오류 제어
			else {
				JOptionPane.showMessageDialog(null, "로그인 오류가 발생하였습니다.");
				System.out.println("Error:로그인 오류가 발생하였습니다.");
			}
		}
		if(e.getSource() == register) {
			try {
				new RegisterGUI();
			} catch (ClassNotFoundException | SQLException e1) {  }
			setVisible(false);
			this.dispose();
			System.out.println("Info:회원가입 창으로 이동합니다.");
		}
	}
}
