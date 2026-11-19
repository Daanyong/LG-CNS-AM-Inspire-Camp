package mylab.bank.entity;

import java.util.ArrayList;
import java.util.List;
import mylab.bank.exception.AccountNotFoundException;
import mylab.bank.exception.InsufficientBalanceException;

public class Bank {
    private List<Account> accounts;
    private int nextAccountNumber;

    public Bank() {
        this.accounts = new ArrayList<>();
        this.nextAccountNumber = 1000;
    }

    public String createSavingsAccount(String ownerName, double balance, double interestRate) {
        String accNum = "AC" + nextAccountNumber++;
        SavingsAccount acc = new SavingsAccount(accNum, ownerName, balance, interestRate);
        accounts.add(acc);
        System.out.println("Saving(저축) 계좌가 생성되었습니다: " + acc);
        return accNum;
    }

    public String createCheckingAccount(String ownerName, double balance, double withdrawalLimit) {
        String accNum = "AC" + nextAccountNumber++;
        CheckingAccount acc = new CheckingAccount(accNum, ownerName, balance, withdrawalLimit);
        accounts.add(acc);
        System.out.println("체킹 계좌가 생성되었습니다: " + acc);
        return accNum;
    }

    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        throw new AccountNotFoundException("계좌번호 " + accountNumber + "에 해당하는 계좌를 찾을 수 없습니다.");
    }

    public void deposit(String accountNumber, double amount) throws AccountNotFoundException {
        Account acc = findAccount(accountNumber);
        acc.deposit(amount);
        System.out.println(amount + "원이 입금되었습니다. 현재 잔액: " + acc.getBalance() + "원");
    }

    public void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InsufficientBalanceException {
        Account acc = findAccount(accountNumber);
        acc.withdraw(amount);
        System.out.println(amount + "원이 출금되었습니다. 현재 잔액: " + acc.getBalance() + "원");
    }

    public void transfer(String fromAccNum, String toAccNum, double amount) throws AccountNotFoundException, InsufficientBalanceException {
        Account fromAcc = findAccount(fromAccNum);
        Account toAcc = findAccount(toAccNum);
        
        fromAcc.withdraw(amount);
        toAcc.deposit(amount);
        System.out.println(amount + "원이 " + fromAccNum + "에서 " + toAccNum + "로 송금되었습니다.");
    }

    public void printAllAccounts() {
        System.out.println("=== 모든 계좌 목록 ===");
        for (Account acc : accounts) {
            System.out.println(acc);
        }
        System.out.println("======================");
    }
}