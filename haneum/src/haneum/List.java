package haneum;

@SuppressWarnings("unused")
public class List {
	private final String name;
	private final String gender;
	private final int age;
	private final float temp;
	private final int bpm;
	private final String ID;
	private final String PW;
	private final boolean admin;
	// List를 클래스로 만들어 구조체 형식 변경
	public List(String name, String gender, int age, float temp, int bpm, String ID, String PW, boolean admin) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.temp = temp;
		this.bpm = bpm;
		this.ID = ID;
		this.PW = PW;
		this.admin = admin;
	}
	// List에서 이름을 가져오는 메소드
	public String getName() {
		return this.name;
	}
	// List에서 성별을 가져오는 메소드
	public String getGender() {
		return this.gender;
	}
	// List에서 나이를 가져오는 메소드
	public int getAge() {
		return this.age;
	}
	// List에서 온도를 가져오는 메소드
	public float getTemp() {
		return this.temp;
	}
	// List에서 심박수를 가져오는 메소드
	public int getBPM() {
		return this.bpm;
	}
	// List에서 아이디를 가져오는 메소드
	public String getID() {
		return this.ID;
	}
	// List에서 비밀번호를 가져오는 메소드
	public String getPW() {
		return this.PW;
	}
	// List에서 관리자 여부를 가져오는 메소드
	public boolean getAdmin() {
		return this.admin;
	}
}
