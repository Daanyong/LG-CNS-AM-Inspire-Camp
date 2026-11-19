package chapter3.oop;

// Run 실행단축키: ctrl+f11

public class MyDateTest {
	public static void main(String[] args) {
		MyDate date = new MyDate();
		// date.day = 32;
		date.setMonth(13);
		System.out.println(date.getMonth());
		date.setDay(31);
		System.out.println(date.getDay());
		
		
		MyDate date2 = new MyDate(2026, 13, 16);
		System.out.println(date2.getMonth());
		System.out.println(date2.getDay());
	}
}
