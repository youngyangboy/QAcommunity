package work.ubox.community.exception;

public enum CustomizeErrorCode implements ICostomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"您找的问题不存在了，换个问题试试？"),
    TARGET_PARAM_NOT_FOUND(2002, "未选中任何问题或者评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004, "服务器冒烟了，请稍后再试~"),
    TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006, "回复的评论不存在"),
    COMMENT_IS_EMPTY(2007, "输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008, "非法操作--读取他人信息"),
    NOTIFICATION_NOT_FOUND(2009,"消息未找到！"),
    FILE_UPLOAD_FAIL(2010,"图片上传失败！"),


    ;
    private Integer code;
    private String message;

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
