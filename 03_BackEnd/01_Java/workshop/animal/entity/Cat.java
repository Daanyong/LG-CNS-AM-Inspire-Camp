package workshop.animal.entity;

public class Cat extends Animal implements Pet {
	private String name;
	
	public Cat(String name) {
		super(4);
		this.name = name;
	}
	
	public Cat() {
		this("");
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}

	@Override
	public void play() {
		System.out.println("후추는 술래잡기를 좋아해");
		
	}

	@Override
	public void eat() {
		System.out.println("후추는 고기를 좋아해");
		
	}
}
