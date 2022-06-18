package etc;

public class Main {
	public static void main(String[] args) {
		try {
			
			(new Serial()).Connect("COM6"); // 연결 시도
		}
		catch(Exception e) { e.printStackTrace(); } // 오류 제어
	}
}
