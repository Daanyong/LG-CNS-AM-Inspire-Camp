package workshop.book.control;

import workshop.book.entity.Magazine;
import workshop.book.entity.Novel;
import workshop.book.entity.Publication;
import workshop.book.entity.ReferenceBook;

public class ManageBook {

	public static void main(String[] args) {
		Magazine pub1 = new Magazine(); 
		//Publication method
		pub1.getPage();
		pub1.getPrice();
		pub1.getPublishingDate();
		pub1.getTitle();
		//Magazine method
		pub1.getPublishingPeriod();
		
		//Poly(다양한) + morphism(변형)		
		Publication pub = new Magazine();
		//Publication method
		pub.getPage();
		pub.getPrice();
		pub.getPublishingDate();
		pub.getTitle();
		
//		Magazine mag = (Magazine)pub;
//		mag.getPublishingPeriod();
		if(pub instanceof Magazine) {
			((Magazine)pub).getPublishingPeriod();
		}
		
		//Publication 부모 타입 배열을 선언 및 생성  Heterogeneous Collection
		Publication[] pubs = new Publication[5];
		pubs[0] = new Magazine("마이크로소프트","2007-10-01",328,9900,"매월");
		pubs[1] = new Magazine("경영과컴퓨터","2007-10-03",316,9000,"매월");
		pubs[2] = new Novel("빠삐용","2007-07-01",396,9800,"베르나르베르베르","현대소설");
		pubs[3] = new Novel("남한산성","2007-04-14",383,11000,"김훈","대하소설");
		pubs[4] = new ReferenceBook("실용주의프로그래머","2007-01-14",496,25000,"소프트웨어공학");	

		for (Publication publication : pubs) {
			System.out.println(publication);
		}
		
		Publication pub3 = pubs[2];
		System.out.println("=== 가격 변경 전 ===");
		System.out.println(pub3.getTitle() + " " + pub3.getPrice());
		
		modifyPrice(pub3);
		
		System.out.println("=== 가격 변경 후 ===");
		System.out.println(pub3.getTitle() + " " + pub3.getPrice());
		
				
	}//main
	
	//pub는 다형적 아규먼트(Polymorphic Argument) Good (1)
	public static void modifyPrice(Publication pub) {
		double rate = 0.0;
		//Magazine이면 40%, Novel이면 20%, ReferenceBook 이면 10%
		if(pub instanceof Magazine) {
			rate = 0.6;
		}
		if(pub instanceof Novel) {
			rate = 0.8;
		}
		if(pub instanceof ReferenceBook) {
			rate = 0.9;
		}
		
		pub.setPrice((int)(pub.getPrice() * rate));
	}
	
	/*
	//Bad
	public static void modifyPrice(Magazine ma) {
		
	}
	
	public static void modifyPrice(Novel no) {
		
	}
	
	public static void modifyPrice(ReferenceBook rb) {
		
	}
	*/
	

}