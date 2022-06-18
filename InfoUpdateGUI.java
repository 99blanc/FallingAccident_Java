package haneum;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class InfoUpdateGUI extends JFrame implements ActionListener {
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
	public int num = 0;
	Integer[] array = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101 };
	JButton back;
	JButton update;
	@SuppressWarnings("rawtypes")
	JComboBox logAge;
	JRadioButton logGenderM;
	JRadioButton logGenderW;
	JTextField logName;
	JPasswordField logPW;
	JPasswordField logConfPW;
	JLabel finID;
	JLabel finTemp;
	JLabel finBPM;
	JLabel finAdmin;
	@SuppressWarnings("static-access")
	public InfoUpdateGUI(int num) throws ClassNotFoundException, SQLException {
		SQLServer SQL = new SQLServer();
		Database DB = new Database();
		SQL.downloadSQL();
		this.num = num;
		listOfDB = DB.getDB();
		back = new JButton("Back");
		update = new JButton("Update");
		logAge = new JComboBox<Integer>(array);
		logGenderM = new JRadioButton("남(M)");
		logGenderW = new JRadioButton("여(W)");
		finID = new JLabel();
		finTemp = new JLabel();
		finBPM = new JLabel();
		finAdmin = new JLabel();
		ButtonGroup logGender = new ButtonGroup();
		logGender.add(logGenderM);
		logGender.add(logGenderW);
		dbName = listOfDB.get(num).getName();
		dbGender = listOfDB.get(num).getGender();
		dbAge = listOfDB.get(num).getAge();
		dbTemp = listOfDB.get(num).getTemp();
		dbBPM = listOfDB.get(num).getBPM();
		dbID = listOfDB.get(num).getID();
		dbPW = listOfDB.get(num).getPW();
		dbAdmin = listOfDB.get(num).getAdmin();
		logName = new JTextField(dbName);
		logPW = new JPasswordField(dbPW);
		logConfPW = new JPasswordField(dbPW);
		setTitle("한이음(노인복지관련) -> 회원정보 화면");
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
		c.add(new JLabel("온도(Temp)", JLabel.CENTER));
		finTemp.setText(String.valueOf(dbTemp));
		c.add(finTemp);
		c.add(new JLabel("심박수(BPM)", JLabel.CENTER));
		finBPM.setText(String.valueOf(dbBPM));
		c.add(finBPM);
		c.add(new JLabel("관리자 여부(Admin)", JLabel.CENTER));
		if (dbAdmin) {
			finAdmin.setText("true");
		}
		else {
			finAdmin.setText("false");
		}
		c.add(finAdmin);
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
		float checkTemp = dbTemp;
		int checkBPM = dbBPM;
		boolean checkAdmin = dbAdmin;
		int checkAge = (int)logAge.getSelectedItem();
		if (logGenderM.isSelected()) {
			checkGender = "M";
		}
		if (logGenderW.isSelected()) {
			checkGender = "W";
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
			// 오류 제어
			else {
				try {
					SQL.updateSQL((String)checkName, (String)checkGender, (int)checkAge, (float)checkTemp, (int)checkBPM, (String)checkID, (String)checkConfPW, (boolean)checkAdmin, (String)dbName);
					if (listOfDB.get(num).getAdmin()) {
						new AdminMenuGUI(this.num);
					}
					else {
						new UserMenuGUI(this.num);
					}
				} catch (ClassNotFoundException | SQLException e1) {  }
				setVisible(false);
				this.dispose();
				JOptionPane.showMessageDialog(null, "성공적으로 회원정보를 수정하였습니다.");
				System.out.println("Info:성공적으로 회원정보를 수정하였습니다.");
			}
		}
		if (e.getSource() == back) {
			if (listOfDB.get(num).getAdmin()) {
				new AdminMenuGUI(this.num);
				System.out.println("Info:관리자 공간 창으로 이동합니다.");
			}
			else {
				new UserMenuGUI(this.num);
				System.out.println("Info:사용자 공간 창으로 이동합니다.");
			}
			setVisible(false);
			this.dispose();
		}
	}
}
