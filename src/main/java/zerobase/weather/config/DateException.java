package zerobase.weather.config;

import lombok.*;
import zerobase.weather.type.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class DateException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String errorMessage;

    public DateException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
