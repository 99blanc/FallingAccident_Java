package haneum;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class UserUpdateGUI extends JFrame implements ActionListener {
	// DB ArrayList 생성
	public ArrayList<List> listOfDB = new ArrayList<>();
	public String dbName;
	public String dbGender;
	public int dbAge;
	public float dbTemp;
	public int dbBPM;
	public String dbID;
	public String dbPW;
	public boolean dbAdmin;
	// 사용자 확인 정수형 변수
	public int thisNum = 0;
	public int otherNum = 0;
	Integer[] array = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };
	JButton back;
	JButton update;
	@SuppressWarnings("rawtypes")
	JComboBox logAge;
	JRadioButton logGenderM;
	JRadioButton logGenderW;
	JRadioButton logAdminT;
	JRadioButton logAdminF;
	JTextField logName;
	JTextField logTemp;
	JTextField logBPM;
	JPasswordField logPW;
	JPasswordField logConfPW;
	JLabel finID;
	@SuppressWarnings("static-access")
	public UserUpdateGUI(int thisNum, int otherNum) throws ClassNotFoundException, SQLException {
		SQLServer SQL = new SQLServer();
		Database DB = new Database();
		SQL.downloadSQL();
		this.thisNum = thisNum;
		this.otherNum = otherNum;
		listOfDB = DB.getDB();
		back = new JButton("Back");
		update = new JButton("회원수정(Update)");
		logAge = new JComboBox<Integer>(array);
		logGenderM = new JRadioButton("남(M)");
		logGenderW = new JRadioButton("여(W)");
		logAdminT = new JRadioButton("관리자(true)");
		logAdminF = new JRadioButton("관리자(false)");
		finID = new JLabel();
		ButtonGroup logGender = new ButtonGroup();
		logGender.add(logGenderM);
		logGender.add(logGenderW);
		ButtonGroup logAdmin = new ButtonGroup();
		logAdmin.add(logAdminT);
		logAdmin.add(logAdminF);
		dbName = listOfDB.get(otherNum).getName();
		dbGender = listOfDB.get(otherNum).getGender();
		dbAge = listOfDB.get(otherNum).getAge();
		dbTemp = listOfDB.get(otherNum).getTemp();
		dbBPM = listOfDB.get(otherNum).getBPM();
		dbID = listOfDB.get(otherNum).getID();
		dbPW = listOfDB.get(otherNum).getPW();
		dbAdmin = listOfDB.get(otherNum).getAdmin();
		logName = new JTextField(dbName);
		logTemp = new JTextField(String.valueOf(dbTemp));
		logBPM = new JTextField(String.valueOf(dbBPM));
		logPW = new JPasswordField(dbPW);
		logConfPW = new JPasswordField(dbPW);
		setTitle("한이음(노인복지관련) -> 회원수정 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		GridLayout grid = new GridLayout(10, 2);
		grid.setVgap(5);
		Container c = getContentPane();
		c.setLayout(grid);
		c.add(new JLabel("ID", JLabel.CENTER));
		finID.setText(dbID);
		c.add(finID);
		c.add(new JLabel("PW", JLabel.CENTER));
		c.add(logPW);
		c.add(new JLabel("Confirm PW", JLabel.CENTER));
		c.add(logConfPW);
		c.add(new JLabel("이름(Name)", JLabel.CENTER));
		c.add(logName);
		c.add(new JLabel("나이(Age)", JLabel.CENTER));
		logAge.setSelectedIndex(dbAge - 65);
		c.add(logAge);
		if (dbGender.equals("M")) {
			logGenderM.setSelected(true);
		}
		else {
			logGenderW.setSelected(true);
		}
		c.add(logGenderM);
		c.add(logGenderW);
		c.add(new JLabel("온도(Temp as Float(2, 0))", JLabel.CENTER));
		c.add(logTemp);
		c.add(new JLabel("심박수(BPM as Int)", JLabel.CENTER));
		c.add(logBPM);
		if (dbAdmin) {
			logAdminT.setSelected(true);
		}
		else {
			logAdminF.setSelected(true);
		}
		c.add(logAdminT);
		c.add(logAdminF);
		c.add(back);
		c.add(update);
		// 버튼 이벤트 추가
		back.addActionListener(this);
		update.addActionListener(this);
		// 패널 크기와 GUI 생성
		setSize(800, 400);
		setVisible(true);
	}
	// 버튼 이벤트 메소드
	@Override
	@SuppressWarnings("static-access")
	public void actionPerformed(ActionEvent e) {
		SQLServer SQL = new SQLServer();
		String checkID = dbID;
		String checkPW = new String(logPW.getPassword());
		String checkConfPW = new String(logConfPW.getPassword());
		String checkName = logName.getText();
		String checkGender = null;
		Float checkTemp = Float.parseFloat(logTemp.getText());
		Integer checkBPM = Integer.parseInt(logBPM.getText());
		boolean checkAdmin = false;
		int checkAge = (int)logAge.getSelectedItem();
		if (logGenderM.isSelected()) {
			checkGender = "M";
		}
		if (logGenderW.isSelected()) {
			checkGender = "W";
		}
		if (logAdminT.isSelected()) {
			checkAdmin = true;
		}
		if (logAdminF.isSelected()) {
			checkAdmin = false;
		}
		// 수정 예외 처리 조건문
		if (e.getSource() == update) {
			if (!checkPW.equals(checkConfPW)) {
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
			else if (!logGenderM.isSelected() && !logGenderW.isSelected()) {
				JOptionPane.showMessageDialog(null, "성별을 선택하세요.");
				System.out.println("Error:성별을 선택하세요.");
			}
			else if (!logAdminT.isSelected() && !logAdminF.isSelected()) {
				JOptionPane.showMessageDialog(null, "관리자 여부를 선택하세요.");
				System.out.println("Error:관리자 여부를 선택하세요.");
			}
			else if (checkTemp < 24 || checkTemp > 43) {
				JOptionPane.showMessageDialog(null, "온전한 체온 혹은 실수만을 입력하세요.");
				System.out.println("Error:온전한 체온 혹은 실수만을 입력하세요.");
			}
			else if (checkBPM > 151 || checkBPM < 19) {
				JOptionPane.showMessageDialog(null, "온전한 심박수 혹은 정수만을 입력하세요.");
				System.out.println("Error:온전한 심박수 혹은 정수만을 입력하세요.");
			}
			// 오류 제어
			else {
				try {
					SQL.updateSQL((String)checkName, (String)checkGender, (int)checkAge, (float)checkTemp, (int)checkBPM, (String)checkID, (String)checkConfPW, (boolean)checkAdmin, (String)dbName);
					new AdminStatusGUI(thisNum, otherNum);
					setVisible(false);
					this.dispose();
				} catch (ClassNotFoundException | SQLException e1) {  }
			JOptionPane.showMessageDialog(null, "성공적으로 회원정보를 수정하였습니다.");
			System.out.println("Info:성공적으로 회원정보를 수정하였습니다.");
			}
		}
		if (e.getSource() == back) {
			try {
				new AdminStatusGUI(thisNum, otherNum);
			} catch (ClassNotFoundException | SQLException e1) {  }
			setVisible(false);
			this.dispose();
			System.out.println("Info:회원목록 창으로 이동합니다.");
		}
	}
}
