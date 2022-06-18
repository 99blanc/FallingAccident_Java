package haneum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	// DB ArrayList 생성
	public static ArrayList<List> listOfDB = new ArrayList<>();
	// 정적 Scanner in 생성
	public static Scanner in = new Scanner(System.in);
	// 정적 DB ArrayList를 이용해 데이터 값 수정
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SQLServer SQL = new SQLServer();
		Database DB = new Database();
		SQL.downloadSQL();
		listOfDB = DB.getDB();
		DB.showDB();
		new LoginGUI();
	}
}
