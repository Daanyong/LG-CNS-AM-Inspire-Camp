package mylab.student.exception;

public class InvalidGradeException extends Exception {
    private String message;

    public InvalidGradeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}