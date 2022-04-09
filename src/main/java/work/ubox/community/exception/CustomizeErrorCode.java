package work.ubox.community.exception;

public enum CustomizeErrorCode implements ICostomizeErrorCode{

    QUESTION_NOT_FOUND("您找的问题不存在了，换个问题试试？"),
    ;
    private String message;

    CustomizeErrorCode(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
