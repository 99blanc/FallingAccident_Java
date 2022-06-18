package haneum;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

@SuppressWarnings("serial")
public class AdminMenuGUI extends JFrame implements ActionListener {
	// 사용자 확인 정수형 변수
	public int num = 0;
	JButton userList;
	JButton infoUpdate;
	JButton logout;
	public AdminMenuGUI(int num) {
		this.num = num;
		userList = new JButton("회원목록(UserList)");
		infoUpdate = new JButton("정보(UserInfo)");
		logout = new JButton("Logout");
		setTitle("한이음(노인복지관련) -> 관리자 공간 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		GridLayout grid = new GridLayout(3, 2);
		grid.setVgap(5);
		Container c = getContentPane();
		c.setLayout(grid);
		c.add(new JLabel("관리자 ", JLabel.RIGHT));
		c.add(new JLabel(" 공간", JLabel.LEFT));
		c.add(userList);
		c.add(infoUpdate);
		c.add(new JLabel("목록", JLabel.CENTER));
		c.add(logout);
		// 버튼 이벤트 추가
		userList.addActionListener(this);
		infoUpdate.addActionListener(this);
		logout.addActionListener(this);
		// 패널 크기와 GUI 생성
		setSize(400, 200);
		setVisible(true);
	}
	// 버튼 이벤트 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logout) {
			try {
				new LoginGUI();
			} catch (ClassNotFoundException | SQLException e1) {  }
			setVisible(false);
			this.dispose();
			System.out.println("Info:로그인 창으로 이동합니다.");
		}
		if (e.getSource() == infoUpdate) {
			try {
				new InfoUpdateGUI(this.num);
			} catch (ClassNotFoundException | SQLException e1) {  }
			setVisible(false);
			this.dispose();
			System.out.println("Info:정보 창으로 이동합니다.");
		}
		if (e.getSource() == userList) {
			try {
				new UserListGUI(this.num);
			} catch (ClassNotFoundException | SQLException e1) {  }
			setVisible(false);
			this.dispose();
			System.out.println("Info:회원목록 창으로 이동합니다.");
		}
	}
}
