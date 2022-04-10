package work.ubox.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.ubox.community.enums.CommentTypeEnum;
import work.ubox.community.exception.CustomizeErrorCode;
import work.ubox.community.exception.CustomizeException;
import work.ubox.community.mapper.CommentMapper;
import work.ubox.community.mapper.QuestionEXTMapper;
import work.ubox.community.mapper.QuestionMapper;
import work.ubox.community.model.Comment;
import work.ubox.community.model.Question;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionEXTMapper questionEXTMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId()==0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getContent() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionEXTMapper.incCommentCount(question);
        }
    }
}
