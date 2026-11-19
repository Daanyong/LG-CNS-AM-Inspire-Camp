package mylab.bank.entity;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String ownerName, double balance, double interestRate) {
        super(accountNumber, ownerName, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
        
        System.out.println(interest + "원이 입금되었습니다. 현재 잔액: " + getBalance() + "원");
        System.out.println("이자 " + interest + "원이 적용되었습니다. 현재 잔액: " + getBalance() + "원");
        
        return interest;
    }

    @Override
    public String toString() {
        return super.toString() + ", 이자율: " + (interestRate * 100) + "%";
    }
}