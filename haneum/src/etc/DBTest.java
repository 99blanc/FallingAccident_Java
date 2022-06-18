package etc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
public class DBTest {
	private static void printList(List<String> l) {
		for(int i = 1; i <= l.size(); i++) 
			System.out.println(i + " : " + l.get(i-1));
	}
    public static Scanner in = new Scanner(System.in);
    public static Connection getConnection() throws ClassNotFoundException, SQLException  {
    	String url = "jdbc:mysql://localhost:3306/sampledb";
        String user = "root";
        String pwd = "1234";
        Connection conn = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, pwd);
        System.out.println("접속");
            
        return conn;
    }

    public static void selectAll() throws ClassNotFoundException, SQLException {    
        Connection conn = getConnection();
        String sql = "SELECT * FROM saram";
        ResultSet rs = null;
        Statement stmt = null;
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);
        
        while(rs.next()) {
            String id = rs.getString(1);
            String name = rs.getString(2);
            int age = rs.getInt(3);
            String passwd = rs.getString(4);
            System.out.println(id + ", "+name+", "+age+" , " +passwd);
        }
        if(rs != null) 
			rs.close();
        if(stmt != null) 
			stmt.close();
        if(conn != null) 
			conn.close();
    }
    
    public static void insert(String id, String name, int age, String passwd) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        String sql = "insert into saram(id, name, age, passwd) values(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, id);
        pstmt.setString(2, name);
        pstmt.setInt(3, age);
        pstmt.setString(4,passwd);
        
        int res = pstmt.executeUpdate();
        if(res > 0){
            System.out.println("처리 완료");
        }
        if(pstmt != null) 
			pstmt.close();
        if(conn != null) 
			conn.close();
    }
    
    public static void update(String id, String name, int age) throws ClassNotFoundException, SQLException {
        Connection conn = getConnection();
        String sql = "update saram set name = ?, age = ? where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setInt(2, age);
        pstmt.setString(3, id);
        
        int res = pstmt.executeUpdate();
        if(res > 0){
            System.out.println("업데이트 완료");
            selectAll();
        } else {
            System.out.println("값이 없습니다.");
		}
    }
    
    public static void delete() throws ClassNotFoundException, SQLException {
        List<String> l = new ArrayList<>();
        Connection conn = getConnection();
        String sherchSql = "select id from saram";
        String deleteSql = "delete from saram where id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sherchSql);
        ResultSet res = pstmt.executeQuery();
        
        while(res.next()) 
           l.add(res.getString(1));
       
        printList(l);
        System.out.println("input id : ");
        int id = in.nextInt();
        String index = l.get(--id);
        PreparedStatement pstmt2 = conn.prepareStatement(deleteSql);
        pstmt2.setString(1, index);
        pstmt2.executeUpdate();
        l.remove(id);
        printList(l);
    }
    
    @SuppressWarnings("unused")
	public static void search(String id) throws ClassNotFoundException, SQLException {
        Connection con = getConnection();
        String sql = "select * from saram where id = ?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, id);
        ResultSet rs = null;
        rs = pstmt.executeQuery();
        String name = null;
        int age = 0;
		String passwd = null;
        
        while(rs.next()) {
        	id = rs.getString("id");
        	name = rs.getString("name");
        	age = rs.getInt("age");
        	passwd = rs.getString("passwd");
        	System.out.println(id + name + age);
        }
    }
    
     public static void login(String id, String passwd) throws ClassNotFoundException, SQLException {
    	 Connection con = getConnection();
         String sql = "select * from saram where id = ? and passwd=?";
         PreparedStatement pstmt = con.prepareStatement(sql);
         pstmt.setString(1, id);
         pstmt.setString(2, passwd);
         ResultSet rs = pstmt.executeQuery();
         
         if(rs.next())
         {
        	 System.out.println("true");
         }
         else
         {
        	 System.out.println("false");
         }  
    }
    
    public static void main(String args[]) throws ClassNotFoundException, SQLException {
    	System.out.println("사람 정보 관리 프로그램");
        String[] menuItems = {"input","output","search","modify","delete","end","login"};
		
        for(int i = 0 ; i < menuItems.length ; i++) {
            System.out.print(i+1+". "+menuItems[i] +" ");
        }
        System.out.println();
        
        int no = 0;
		
        do{
            System.out.println("choice : ");
            no = in.nextInt();
        } while(no < 1 || no > menuItems.length);
        
        String id = null;
        String name =null;
        int age = 0;
        String passwd = null;
        
        switch(no){
			case 1:
				id = in.next();
				name = in.next();
				age = in.nextInt();
				passwd = in.next();
				insert(id,name,age,passwd);
				break;
			case 2:
				selectAll(); 
				break;
			case 3:
				System.out.println("검색할 아이디를 입력하세요");
				id = in.next();
				search(id);
				break;
			case 4:
				System.out.println("업데이트 시킬 아이디와 변경할 이름과 나이를 입력하세요");
				id = in.next();
				name = in.next();
				age = in.nextInt();
				update(id, name, age); 
				System.out.println("업데이트");
				break;
			case 5:
				delete();
				break;
			case 6: 
				System.out.println("종료합니다.");
				System.exit(0);
			case 7:
				System.out.println("id를 입력하세요, pw를입력하세요");
				id = in.next();
				passwd = in.next();
				login(id,passwd);
				break;
        }
        main(new String[]{""});  //재귀호출
    }
}