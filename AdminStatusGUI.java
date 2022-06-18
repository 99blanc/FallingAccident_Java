package haneum;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class AdminStatusGUI extends JFrame implements ActionListener {
	// DB ArrayList 생성
	public ArrayList<List> listOfDB = new ArrayList<>();
	public String dbName;
	// 사용자 확인 정수형 변수
	public int thisNum = 0;
	public int otherNum = 0;
	JButton info;
	JButton check;
	JButton back;
	JTextField logName;
	@SuppressWarnings("static-access")
	public AdminStatusGUI(int thisNum, int otherNum) throws ClassNotFoundException, SQLException {
		SQLServer SQL = new SQLServer();
		Database DB = new Database();
		SQL.downloadSQL();
		this.thisNum = thisNum;
		this.otherNum = otherNum;
		listOfDB = DB.getDB();
		info = new JButton("회원수정(Update)");
		check = new JButton("회원상태(Check)");
		back = new JButton("Back");
		dbName = listOfDB.get(otherNum).getName();
		logName = new JTextField(dbName);
		setTitle("한이음(노인복지관련) -> 회원설정 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		GridLayout grid = new GridLayout(3, 2);
		grid.setVgap(5);
		Container c = getContentPane();
		c.setLayout(grid);
		c.add(new JLabel("회원 ", JLabel.RIGHT));
		c.add(new JLabel(" 설정(" + dbName + ")", JLabel.LEFT));
		c.add(info);
		c.add(check);
		c.add(new JLabel("목록", JLabel.CENTER));
		c.add(back);
		// 버튼 이벤트 추가
		info.addActionListener(this);
		check.addActionListener(this);
		back.addActionListener(this);
		// 패널 크기와 GUI 생성
		setSize(400, 200);
		setVisible(true);
	}
	// 버튼 이벤트 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == info) {
			try {
				new UserUpdateGUI(thisNum, otherNum);
			} catch (ClassNotFoundException | SQLException e1) {  }
			setVisible(false);
			this.dispose();
			System.out.println("Info:회원수정 창으로 이동합니다.");
		}
		if (e.getSource() == check) {
			try {
				new AdminCheckGUI(thisNum, otherNum);
			} catch (ClassNotFoundException | SQLException e1) {  }
			setVisible(false);
			this.dispose();
			System.out.println("Info:회원상태 창으로 이동합니다.");
		}
		if (e.getSource() == back) {
			try {
				new UserListGUI(thisNum);
			} catch (ClassNotFoundException | SQLException e1) {  }
			setVisible(false);
			this.dispose();
			System.out.println("Info:회원목록 창으로 이동합니다.");
		}
	}
}
