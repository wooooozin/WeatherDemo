package zerobase.weather.config;

import lombok.*;
import zerobase.weather.type.ErrorCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public DateException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
