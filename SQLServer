package haneum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLServer {
    // 데이터베이스 연결
    // 서버 이름과 유저 로그인 이름 그리고 비밀번호 입력
	// SQL 연결 메소드
	public static Connection connectSQL() throws ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		// 1433(->SQL Server 구성관리자에서 TCP/IP 설정한 PortNumber)
		//databaseName=SQL Server Management Studio에서 만든 데이터베이스 이름
		// ksh, 123456는 위에 설정한 SQL서버 로그인 이름과 비밀번호 입력을 의미
		String url="jdbc:sqlserver://localhost:1433;databaseName=ksh;encrypt=true;trustServerCertificate=true";
		Connection c = null;
		// DB와 연결하는 메소드
		try {
			c = DriverManager.getConnection(url,"ksh","123456");
			// DB 연결 확인 조건문
			if(c != null ) {
	    		System.out.println("DB Connect Success");
	    		System.out.println("[runSQL()]");
	    	} 
	    	else {
	    		System.out.println("DB Connect Faile");
	    	}
		} catch (SQLException e) { e.printStackTrace(); } // 오류 제어
		return c; // 커넥션 값 반환
	}
	// SQL 로드 메소드
	public static void downloadSQL() throws ClassNotFoundException, SQLException {
		Connection c = connectSQL();
		Statement stmt = c.createStatement();
    	ResultSet rs = stmt.executeQuery("select * from userDB");
    	Database DB = new Database();
    	int i = 1;
    	// DB 등록 반복문
    	while (rs.next()) {
    		System.out.println("[" + i + "]" + " " + rs.getString("name")); // ID, PW 검색 성공
    		DB.addDB(rs.getString("name"), rs.getString("gender"), rs.getInt("age"), rs.getFloat("temp"), rs.getInt("bpm"), rs.getString("ID"), rs.getString("PW"), rs.getBoolean("admin")); // 데이터 입력 성공
    		i++;
    	}
    	// SQL 연결 해제 조건문
        if(rs != null) {
			rs.close();
        }
        if(stmt != null) {
			stmt.close();
        }
        if(c != null) {
			c.close();
        }
        System.out.println("[downloadSQL()]");
	}
	// SQL 삽입 메소드
	public static void insertSQL(String name, String gender, int age, float temp, int bpm, String ID, String PW, boolean admin) throws ClassNotFoundException, SQLException {
		Connection c = connectSQL();
		PreparedStatement pstmt = c.prepareStatement("insert into userDB values(?, ?, ?, ?, ?, ?, ?, ?)");
		// DB 삽입 제어문
		pstmt.setString(1, name);
		pstmt.setString(2, gender);
		pstmt.setInt(3, age);
		pstmt.setFloat(4, temp);
		pstmt.setInt(5, bpm);
		pstmt.setString(6, ID);
		pstmt.setString(7, PW);
		pstmt.setBoolean(8, admin);
		int res = pstmt.executeUpdate();
		// DB 삽입 완료 조건문
        if(res > 0) {
            System.out.println("[insertSQL()]");
        }
        else {
        	System.out.println("Error:삽입에 실패하였습니다.");
        }
        // SQL 연결 해제 조건문
        if(pstmt != null) {
			pstmt.close();
        }
        if(c != null) {
			c.close();
        }
	}
	// SQL 삭제 메소드
	public static void deleteSQL(String name) throws ClassNotFoundException, SQLException {
		Connection c = connectSQL();
		PreparedStatement pstmt = c.prepareStatement("delete from userDB where name = ?");
		// DB 삭제 제어문
		pstmt.setString(1, name);
		int res = pstmt.executeUpdate();
		// DB 삭제 완료 조건문
		if (res > 0) {
			System.out.println("[deleteSQL()]");
		} 
		else {
			System.out.println("Error:삭제에 실패하였습니다.");
		}
		// SQL 연결 해제 조건문
        if(pstmt != null) {
			pstmt.close();
        }
        if(c != null) {
			c.close();
        }
	}
	// SQL 수정 메소드
	public static void updateSQL(String name, String gender, int age, float temp, int bpm, String ID, String PW, boolean admin, String oldname) throws ClassNotFoundException, SQLException {
		Connection c = connectSQL();
		PreparedStatement pstmt = c.prepareStatement("update userDB set name = ?, gender = ?, age = ?, temp = ?, bpm = ?, ID = ?, PW = ?, admin = ? where name = ?");
		// DB 수정 제어문
		pstmt.setString(1, name);
		pstmt.setString(2, gender);
		pstmt.setInt(3, age);
		pstmt.setFloat(4, temp);
		pstmt.setInt(5, bpm);
		pstmt.setString(6, ID);
		pstmt.setString(7, PW);
		pstmt.setBoolean(8, admin);
		pstmt.setString(9, oldname);
		int res = pstmt.executeUpdate();
		// DB 수정 완료 조건문
        if(res > 0) {
            System.out.println("[updateSQL()]");
        }
        else {
        	System.out.println("Error:수정에 실패하였습니다.");
        }
        // SQL 연결 해제 조건문
        if(pstmt != null) {
			pstmt.close();
        }
        if(c != null) {
			c.close();
        }
	}
}
