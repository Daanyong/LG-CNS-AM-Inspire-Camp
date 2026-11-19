package workshop.account.control;

import workshop.account.entity.Account;
import workshop.account.exception.InsufficientBalanceException;

public class AccountTest {
	public static void main(String[] args) {
		try {
			Account account = new Account();
			
			account.setCustId("A1100");
			account.setAcctId("221-22-3477");
			account.deposit(100000);
			
			System.out.println("고객번호= " + account.getCustId());
			System.out.println("계좌번호= " + account.getAcctId());
			System.out.println("잔액= " + account.getBalance());
		
			Account account2 = new Account("A1105", "221-22-3488", 100000);
			account2.withdraw(50000);
			System.out.println("고객번호= " + account2.getCustId());
			System.out.println("계좌번호= " + account2.getAcctId());
			System.out.println("잔액= " + account2.getBalance());
			
			// toString();
			// getClass().getName() + '@' + Integer.toHexString(hashCode())
			// override 전: hash코드로 출력됨 -> workshop.account.entity.Account@2a139a55
			// override 후: Account [custId=A1105, acctId=221-22-3488, balance=50000]
			System.out.println(account2);
		} catch (InsufficientBalanceException e) {
			System.out.println("Error 메시지 = " + e.getMessage() + e.getCurrentBalance());
		}
	}
}
