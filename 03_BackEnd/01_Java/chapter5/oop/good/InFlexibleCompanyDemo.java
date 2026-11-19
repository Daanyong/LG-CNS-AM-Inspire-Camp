package chapter5.oop.good;

public class InFlexibleCompanyDemo {
    public static void main(String[] args) {
        // Employee e = new Employee();
    	Employee mereClerk1 = new MereClerk("철수", 100);
    	Employee mereClerk2 = new MereClerk("영희", 100);
    	
    	Employee manager = new Manager("홍길동", 200, " 인사부"); //List list = new ArrayList();
    	Manager manager2 = new Manager("둘리", 250, " 개발부"); // ArrayList list2 = new ArrayList();
    	    	
    	// Employee 타입
    	System.out.println(manager.getName() + " " + manager.getSalary());
    	// Casting Employee를 Manager로 형변환
    	Manager m = (Manager)manager;
    	m.getDepartment();
    	
    	// Manager 타입
    	System.out.println(manager2.getName() + " " + manager2.getSalary() + " " + manager2.getDepartment());
    	    	
    	
//        MereClerk mereClerk1 = new MereClerk("철수", 100);
//        MereClerk mereClerk2 = new MereClerk("영희", 100);
//        Manager manager = new Manager("홍길동", 200);

        
        // Employee 타입 배열 선언 Heterogeneous Collection
    	Employee[] emps = new Employee[3];
    	emps[0] = new MereClerk("철수", 100);
    	emps[1] = new MereClerk("영희", 100);
    	emps[2] = new Manager("둘리", 250, " 개발부");
    	
    	for (Employee employee : emps) {
    		System.out.println("현재 월급입니다.");
    		if(employee instanceof Manager) {
    			System.out.print("부서명 =" + ((Manager)employee).getDepartment() + " 관리자 ");
    		}
    		System.out.println(employee.getName() + "의 현재 월급은 " + employee.getSalary() + " 만원 입니다.");
    		    		
    		System.out.println("올린 후의 월급입니다.");
    		employee.manageSalary(10);
    		System.out.println(employee.getName() + "의 현재 월급은 " + employee.getSalary() + " 만원 입니다.");
    		
    		System.out.println("");
    	}
   }
}

