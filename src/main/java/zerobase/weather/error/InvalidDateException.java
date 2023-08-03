package zerobase.weather.error;

public class InvalidDateException extends RuntimeException {
    private static final String MESSAGE = "오랜 과거 혹은 너무 먼 미래의 날짜 입니다.";

    public InvalidDateException() {
        super(MESSAGE);
    }
}
