package haneum;

import java.util.ArrayList;

public class Database {
	// DB ArrayList 생성
	public static ArrayList<List> DB = new ArrayList<>();
	// DB를 가져오는 메소드
	public ArrayList<List> getDB() {
		System.out.println("[getDB()]");
		for (int i = 0; i < DB.size(); i++) {
			System.out.println(DB.get(i).getName() + " " + DB.get(i).getGender() + " " + DB.get(i).getAge() + " " +DB.get(i).getTemp() + " " + DB.get(i).getBPM() + " " + DB.get(i).getID() + " " + DB.get(i).getPW() + " " + DB.get(i).getAdmin());
		}
		return DB;
	}
	// DB에 추가하는 메소드
	public void addDB(String name, String gender, int age, float temp, int bpm, String ID, String PW, boolean admin) {
		List tmp = new List(name, gender, age, temp, bpm, ID, PW, admin);
		for (int i = 0; i < DB.size(); i++) {
			if (DB.get(i).getID().equals(ID)) {
				DB.remove(i);
				break;
			}
		}
		System.out.println("[addDB()]");
		DB.add(tmp);
		System.out.println(name + " " + gender + " " + age + " " + temp + " " + bpm + " " + ID + " " + PW + " " + admin + " " + "Location:(" + tmp + ")");
	}
	// DB를 보여주는 메소드
	public void showDB() {
		System.out.println("[showDB()]");
		for (int i = 0; i < DB.size(); i++) {
			System.out.println(DB.get(i).getName() + " " + DB.get(i).getGender() + " " + DB.get(i).getAge() + " " + DB.get(i).getTemp() + " " + DB.get(i).getBPM() + " " + DB.get(i).getID() + " " + DB.get(i).getPW() + " " + DB.get(i).getAdmin());
		}
	}
}
