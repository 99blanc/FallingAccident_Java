package haneum;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class BodyCheckGUI extends JFrame implements ActionListener {
	// DB ArrayList 생성
	public ArrayList<List> listOfDB = new ArrayList<>();
	public String dbName;
	public String dbGender;
	public int dbAge;
	public float dbTemp;
	public int dbBPM;
	// 사용자 확인 정수형 변수
	public int num = 0;
	JButton back;
	JLabel infoBPM;
	JLabel infoTemp;
	@SuppressWarnings("static-access")
	public BodyCheckGUI(int num) throws ClassNotFoundException, SQLException {
		SQLServer SQL = new SQLServer();
		Database DB = new Database();
		SQL.downloadSQL();
		this.num = num;
		listOfDB = DB.getDB();
		back = new JButton("Back");
		infoBPM = new JLabel();
		infoTemp = new JLabel();
		dbName = listOfDB.get(num).getName();
		dbGender = listOfDB.get(num).getGender();
		dbAge = listOfDB.get(num).getAge();
		dbTemp = listOfDB.get(num).getTemp();
		dbBPM = listOfDB.get(num).getBPM();
		setTitle("한이음(노인복지관련) -> 사용자 상태 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		GridLayout grid = new GridLayout(6, 2);
		grid.setVgap(5);
		Container c = getContentPane();
		c.setLayout(grid);
		c.add(new JLabel("사용자 ", JLabel.RIGHT));
		c.add(new JLabel(" 상태", JLabel.LEFT));
		c.add(new JLabel());
		c.add(new JLabel());
		c.add(new JLabel("이름(Name) " + dbName + " /", JLabel.RIGHT));
		c.add(new JLabel(" 성별(Gender) " + dbGender + " / 나이(Age) " + dbAge, JLabel.LEFT));
		c.add(new JLabel("심박수(BPM)", JLabel.CENTER));
		infoBPM.setOpaque(true);
		if (dbBPM <= 60 || dbBPM >= 80) {
			infoBPM.setText("위험");
			infoBPM.setForeground(Color.RED);
		}
		else if ((dbBPM <= 69 && dbBPM >= 61) || (dbBPM <= 79 && dbBPM >= 75)) {
			infoBPM.setText("주의");
			infoBPM.setForeground(Color.ORANGE);
		}
		else if (dbBPM <= 76 && dbBPM >= 70) {
			infoBPM.setText("정상");
			infoBPM.setForeground(Color.GREEN);
		}
		else {
			infoBPM.setText("오류");
			infoBPM.setForeground(Color.GRAY);
		}
		c.add(infoBPM);
		c.add(new JLabel("체온(Temp)", JLabel.CENTER));
		infoTemp.setOpaque(true);
		if (dbTemp <= 34.0 || dbTemp >= 38.0) {
			infoTemp.setText("위험");
			infoTemp.setForeground(Color.RED);
		}
		else if ((dbTemp <= 37.9 && dbTemp >= 37.6) || (dbTemp <= 35.7 && dbTemp >= 34.1)) {
			infoTemp.setText("주의");
			infoTemp.setForeground(Color.ORANGE);
		}
		else if (dbTemp <= 37.5 && dbTemp >= 35.8) {
			infoTemp.setText("정상");
			infoTemp.setForeground(Color.GREEN);
		}
		else {
			infoTemp.setText("오류");
			infoTemp.setForeground(Color.GRAY);
		}
		c.add(infoTemp);
		c.add(new JLabel("목록", JLabel.CENTER));
		c.add(back);
		// 버튼 이벤트 추가
		back.addActionListener(this);
		// 패널 크기와 GUI 생성
		setSize(600, 200);
		setVisible(true);
	}
	// 버튼 이벤트 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == back) {
			new UserMenuGUI(this.num);
			setVisible(false);
			this.dispose();
			System.out.println("Info:사용자 공간 창으로 이동합니다.");
		}
	}
}
