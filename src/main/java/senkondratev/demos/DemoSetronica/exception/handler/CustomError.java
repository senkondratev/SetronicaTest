package senkondratev.demos.DemoSetronica.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CustomError {
    private ErrorCode errorCode;
    private String errorMessage;
}
