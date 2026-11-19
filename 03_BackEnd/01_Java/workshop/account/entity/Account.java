package workshop.account.entity;

import workshop.account.exception.InsufficientBalanceException;

public class Account {
	private String custId;
	private String acctId;
	private int balance;
	
	public Account() {
		
	}

	public Account(String custId, String acctId, int balance) { 
		this.custId = custId;
		this.acctId = acctId;
		this.balance = balance;
	}

	// Method 선언
	public String getCustId() {
		return custId;
	}
	
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
	public String getAcctId() {
		return acctId;
	}
	
	public void setAcctId(String acctId) {
		this.acctId = acctId;
	}
	
	public int getBalance() {
		return balance;
	}

	public void deposit(int amount) {
//		this.balance = this.balance + amount;
		this.balance += amount;
	}
	
	public void withdraw(int amount) throws InsufficientBalanceException {
		if(amount > this.balance) {
			// exception을 강제로 발생시킴
			throw new InsufficientBalanceException("잔액이 부족합니다. 현재 잔액은 = ", this.balance);
		}
		this.balance -= amount;
	}

	@Override
	public String toString() {
		return "Account [custId=" + custId + ", acctId=" + acctId + ", balance=" + balance + "]";
	}
	
	
}
