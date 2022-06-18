package haneum;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class RegisterGUI extends JFrame implements ActionListener {
	// DB ArrayList 생성
	public ArrayList<List> listOfDB = new ArrayList<>();
	public boolean existID = false;
	Integer[] array = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };
	JButton login;
	JButton register;
	JTextField logName;
	@SuppressWarnings("rawtypes")
	JComboBox logAge;
	JRadioButton logGenderM;
	JRadioButton logGenderW;
	JTextField logID;
	JPasswordField logPW;
	JPasswordField logConfPW;
	@SuppressWarnings("static-access")
	public RegisterGUI() throws ClassNotFoundException, SQLException {
		SQLServer SQL = new SQLServer();
		Database DB = new Database();
		SQL.downloadSQL();
		listOfDB = DB.getDB();
		logName = new JTextField("");
		logAge = new JComboBox<Integer>(array);
		logAge.setSelectedItem(0);
		logGenderM = new JRadioButton("남(M)");
		logGenderM.setSelected(true);
		logGenderW = new JRadioButton("여(W)");
		logID = new JTextField("");
		logPW = new JPasswordField("");
		logConfPW = new JPasswordField("");
		login = new JButton("Login");
		register = new JButton("Register");
		ButtonGroup logGender = new ButtonGroup();
		logGender.add(logGenderM);
		logGender.add(logGenderW);
		setTitle("한이음(노인복지관련) -> 회원가입 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		GridLayout grid = new GridLayout(7, 2);
		grid.setVgap(5);
		Container c = getContentPane();
		c.setLayout(grid);
		c.add(new JLabel("이름(Name)", JLabel.CENTER));
		c.add(logName);
		c.add(new JLabel("나이(Age)", JLabel.CENTER));
		c.add(logAge);
		c.add(logGenderM);
		c.add(logGenderW);
		c.add(new JLabel("ID", JLabel.CENTER));
		c.add(logID);
		c.add(new JLabel("PW", JLabel.CENTER));
		c.add(logPW);
		c.add(new JLabel("Confirm PW", JLabel.CENTER));
		c.add(logConfPW);
		c.add(login);
		c.add(register);
		// 버튼 이벤트 추가
		login.addActionListener(this);
		register.addActionListener(this);
		// 패널 크기와 GUI 생성
		setSize(600, 300);
		setVisible(true);
	}
	// 버튼 이벤트 메소드
	@Override
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		SQLServer SQL = new SQLServer();
		String checkID = logID.getText();
		String checkPW = new String(logPW.getPassword());
		String checkConfPW = new String(logConfPW.getPassword());
		String checkName = logName.getText();
		String checkGender = "M";
		// 나이 치환
		int checkAge = (int)logAge.getSelectedItem();
		// 성별 치환
		if (logGenderM.isSelected()) {
			checkGender = "M";
		}
		if (logGenderW.isSelected()) {
			checkGender = "W";
		}
		if(e.getSource() == register) {
			for (int i = 0; i < listOfDB.size(); i++) {
				// 사용자 로그인 아이디 확인 조건문
				if (checkID.equals(listOfDB.get(i).getID())) {
					existID = true;
					break;
				}
			}
			// 회원가입 예외 처리 조건문
			if (existID) {
				JOptionPane.showMessageDialog(null, "이미 존재하는 ID입니다.");
				System.out.println("Error:이미 존재하는 ID입니다.");
			}
			else if (!checkPW.equals(checkConfPW)) {
				JOptionPane.showMessageDialog(null, "PW 불일치 오류가 발생하였습니다.");
				System.out.println("Error:PW 불일치 오류가 발생하였습니다.");
			}
			else if (checkName.length() < 1) {
				JOptionPane.showMessageDialog(null, "이름을 입력하세요.");
				System.out.println("Error:이름을 입력하세요.");
			}
			else if (checkName.length() < 3 && checkName.length() > 6){
				JOptionPane.showMessageDialog(null, "이름은 2글자보다 크거나 6글자보다 적어야 합니다.");
				System.out.println("Error:이름은 2글자보다 크거나 6글자보다 적어야 합니다.");
			}
			else if (logAge.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "나이를 선택하세요.");
				System.out.println("Error:나이를 선택하세요.");
			}
			// 오류 제어
			else {
				try {
					SQL.insertSQL((String)checkName, (String)checkGender, (int)checkAge, (float)36.5, 70, (String)checkID, (String)checkConfPW, (boolean)false);
					new LoginGUI();
				} catch (ClassNotFoundException | SQLException e1) {  }
				setVisible(false);
				this.dispose();
				JOptionPane.showMessageDialog(null, "성공적으로 회원가입하였습니다.");
				System.out.println("Info:성공적으로 회원가입하였습니다.");
			}
		}
		if(e.getSource() == login) {
			try {
				new LoginGUI();
			} catch (ClassNotFoundException | SQLException e1) {  }
			setVisible(false);
			this.dispose();
			System.out.println("Info:로그인 창으로 이동합니다.");
		}
	}
}
