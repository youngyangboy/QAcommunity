package work.ubox.community.exception;

public class CustomizeException extends RuntimeException {
    private String  message;

    public CustomizeException(ICostomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
