package workshop.person.control;

import workshop.person.entity.PersonEntity;

public class PersonManager {
	public static void main(String[] args) {
		PersonManager pManager = new PersonManager();
		// PersonEntity 배열 선언 및 생성
		PersonEntity[] persons = new PersonEntity[10];
		pManager.fillPersons(persons);
		pManager.showPersons(persons);
		
		char gender = '여';
		System.out.print("성별 '" + gender + "'은(는) ");
		System.out.println(pManager.findByGender(persons, gender) + "명입니다.");
		
		String name = "김하늘";
		pManager.showPerson(persons, name);
	}

	// PersonEntity 배열에 값을 채워 넣는 메서드
	public void fillPersons(PersonEntity[] persons) {
		persons[0] = new PersonEntity("이성호","7212121028102", "인천 계양구", "032-392-2932");
		persons[1] = new PersonEntity("김하늘","7302132363217", "서울 강동구", "02-362-1932");
		persons[2] = new PersonEntity("박영수","7503111233201", "서울 성북구", "02-887-1542");
		persons[3] = new PersonEntity("나인수","7312041038988", "대전 유성구", "032-384-2223");
		persons[4] = new PersonEntity("홍정수","7606221021341", "서울 양천구", "02-158-7333");
		persons[5] = new PersonEntity("이미숙","7502142021321", "서울 강서구", "02-323-1934");
		persons[6] = new PersonEntity("박성구","7402061023101", "서울 종로구", "02-308-0932");
		persons[7] = new PersonEntity("유성미","7103282025101", "서울 은평구", "02-452-0939");
		persons[8] = new PersonEntity("황재현","7806231031101", "인천 중구", "032-327-2202");
		persons[9] = new PersonEntity("최철수","7601211025101", "인천 계양구", "032-122-7832");		
	}
	
	// PersonEntity 배열의 값을 출력하는 메서드
	public void showPersons(PersonEntity[] persons) {
		for (PersonEntity person : persons) {
			System.out.println("[이름] " + person.getName() + "\t [성별] " +
					person.getGender() + "\t [주소] " + person.getAddress());
		}
	}
	
	// 입력받은 성별 값과 일치하는 PersonEntity 객체의 건수를 찾는 메서드
	public int findByGender(PersonEntity[] persons, char gender) {
		int personCnt = 0;
		for (PersonEntity person : persons) {
			if(person.getGender() == gender) {
				personCnt++;
			}
		}
		return personCnt;
	}
	
	// 입력 받은 이름 값과 일치하는 PersonEntity 객체를 찾아서 세부정보를 출력하는 메서드
	public void showPerson(PersonEntity[] persons, String name) {
		for(PersonEntity person : persons) {
			if(person.getName().contentEquals(name)) {
				
				System.out.println("[이름] " + person.getName());
				System.out.println("[성별] " + person.getGender());
				System.out.println("[전화번호] " + person.getPhone());
				System.out.println("[주소] " + person.getAddress());	
				
				break;
			}
			
		}
	}
}
