package zerobase.weather.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생"),
    INVALID_REQUEST("잘못된 요청입니다."),
    DATE_TYPE_WRONG("날짜 입력 형식이 올바르지 않습니다."),
    DATE_TOO_FAR_TOO_PAST("오랜 과거 혹은 너무 먼 미래의 날짜 입니다.");
    private final String description;
}
