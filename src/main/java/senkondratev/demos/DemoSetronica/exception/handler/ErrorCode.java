package senkondratev.demos.DemoSetronica.exception.handler;

public enum ErrorCode {
    NO_SUCH_ID(0, "Couldn't find entity with provided id"),
    CANNOT_DELETE(1, "Couldn't delete entity with provided id"),
    VALIDATION_FAILED(2, "Provided DTO was not valid!"),
    CONSTRAINT_VIOLATION(3, "Invalid database operation. Check log for more info."),
    BAD_USER_REQUEST(4, "There's no product with provided parameters"),
    ILLEGAL_ARGUMENT(5, "There's no product with provided parameters");

    private int errorCode;
    private String errorName;

    ErrorCode(int errorCode, String errorName) {
        this.errorCode = errorCode;
        this.errorName = errorName;
    }
}
