package mylab.bank.exception;

// WithdrawalLimitExceededException.java
public class WithdrawalLimitExceededException extends InsufficientBalanceException {
    public WithdrawalLimitExceededException(String message) {
        super(message);
    }
}