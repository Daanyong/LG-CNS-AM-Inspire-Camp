package chapter5.oop.good;
/* 관리자 클래스 */
public class Manager extends Employee {
	// 부서명
	private String department;
	
    public Manager (String name, double salary) {
    	super(name, salary);
    }

    public Manager (String name, double salary, String department) {
    	this(name, salary);
    	this.department = department;
    }
    
    @Override
	public void manageSalary(double rate) {
        salary = salary+ salary*(rate/100);
        salary += 20; // 20만원을 추가로 받는다.
    }
	
	public String getDepartment() {
		return department;
	}
}
