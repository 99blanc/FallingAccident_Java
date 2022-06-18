package haneum;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class UserListGUI extends JFrame implements ActionListener {
	// DB ArrayList 생성
	public ArrayList<List> listOfDB = new ArrayList<>();
	public String dbName;
	// 정적 페이징 처리 정수형 변수 선언
	public static int firstPage = 0;
	public static int thisPage = firstPage;
	public static int lastPage;
	public static int lengthPage = 5;
	public static int countDB = 0;
	public static ArrayList<JButton> listButton = new ArrayList<>(lengthPage);
	// 사용자 수정 GUI 로드를 위한 bool형 변수 선언
	public boolean isChecked = true;
	// 사용자 확인 정수형 변수
	public int num = 0;
	JButton user1 = new JButton();
	JButton user2 = new JButton();
	JButton user3 = new JButton();
	JButton user4 = new JButton();
	JButton user5 = new JButton();
	JButton prev;
	JButton next;
	JButton back;
	@SuppressWarnings("static-access")
	public UserListGUI(int num) throws ClassNotFoundException, SQLException {
		// 버튼 ArrayList 생성 후 적용
		SQLServer SQL = new SQLServer();
		Database DB = new Database();
		SQL.downloadSQL();
		this.num = num;
		listOfDB = DB.getDB();
		listButton.add(user1);
		listButton.add(user2);
		listButton.add(user3);
		listButton.add(user4);
		listButton.add(user5);
		prev = new JButton("이전(Prev)");
		next = new JButton("다음(Next)");
		back = new JButton("Back");
		setTitle("한이음(노인복지관련) -> 회원목록 화면");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		GridLayout grid = new GridLayout(8, 2);
		grid.setVgap(5);
		Container c = getContentPane();
		c.setLayout(grid);
		c.add(new JLabel("회원 ", JLabel.RIGHT));
		c.add(new JLabel(" 목록", JLabel.LEFT));
		for (int i = 0; i < listOfDB.size(); i++) {
			lastPage += 1;
		}
		lastPage = (int)Math.ceil(lastPage/lengthPage);
		try {
			int j = 0;
			for (int i = countDB; i < countDB + lengthPage; i++) {
				if (listOfDB.get(i).getName() != null) {
					dbName = listOfDB.get(i).getName();
				}
				else {
					dbName = " ";
				}
				c.add(new JLabel("이름(Name)", JLabel.CENTER));
				c.add(listButton.get(j));
				listButton.get(j).setText(dbName);
				listButton.get(j).addActionListener(this);
				j++;
			}
		} catch (IndexOutOfBoundsException e1) {  }
		c.add(prev);
		c.add(next);
		c.add(new JLabel("목록", JLabel.CENTER));
		c.add(back);
		// 버튼 이벤트 추가
		prev.addActionListener(this);
		next.addActionListener(this);
		back.addActionListener(this);
		// 패널 크기와 GUI 생성
		setSize(600, 300);
		setVisible(true);
	}
	// 버튼 이벤트 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == prev) {
			countDB -= lengthPage;
			if (countDB <= 0) {
				countDB = 0;
			}
			try {
				new UserListGUI(this.num);
				setVisible(false);
				this.dispose();
				System.out.println("Info:이전 회원목록 창으로 이동합니다.");
			} catch (ClassNotFoundException | SQLException e1) {  }
		}
		if (e.getSource() == next) {
			countDB += lengthPage;
			if (countDB >= lastPage*lengthPage) {
				countDB = lastPage*lengthPage;
			}
			try {
				new UserListGUI(this.num);
				setVisible(false);
				this.dispose();
				System.out.println("Info:다음 회원목록 창으로 이동합니다.");
			} catch (ClassNotFoundException | SQLException e1) {  }
		}
		if (e.getSource() == back) {
			new AdminMenuGUI(this.num);
			setVisible(false);
			this.dispose();
			System.out.println("Info:관리자 공간 창으로 이동합니다.");
		}
		if (isChecked) {
			if (e.getSource() == listButton.get(0)) {
				try {
					new AdminStatusGUI(this.num, countDB);
				} catch (ClassNotFoundException | SQLException e1) {  }
				setVisible(false);
				this.dispose();
				System.out.println("Info:사용자[" + countDB + "] 회원설정 창으로 이동합니다.");
			}
			if (e.getSource() == listButton.get(1)) {
				try {
					new AdminStatusGUI(this.num, countDB + 1);
				} catch (ClassNotFoundException | SQLException e1) {  }
				setVisible(false);
				this.dispose();
				System.out.println("Info:사용자[" + countDB + 1 + "] 회원설정 창으로 이동합니다.");
			}
			if (e.getSource() == listButton.get(2)) {
				try {
					new AdminStatusGUI(this.num, countDB + 2);
				} catch (ClassNotFoundException | SQLException e1) {  }
				setVisible(false);
				this.dispose();
				System.out.println("Info:사용자[" + countDB + 2 + "] 회원설정 창으로 이동합니다.");
			}
			if (e.getSource() == listButton.get(3)) {
				try {
					new AdminStatusGUI(this.num, countDB + 3);
				} catch (ClassNotFoundException | SQLException e1) {  }
				setVisible(false);
				this.dispose();
				System.out.println("Info:사용자[" + countDB + 3 + "] 회원설정 창으로 이동합니다.");
			}
			if (e.getSource() == listButton.get(4)) {
				try {
					new AdminStatusGUI(this.num, countDB + 4);
				} catch (ClassNotFoundException | SQLException e1) {  }
				setVisible(false);
				this.dispose();
				System.out.println("Info:사용자[" + countDB + 4 + "] 회원설정 창으로 이동합니다.");
			}
			isChecked = false;
		}
	}
}
