package work.ubox.community.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import work.ubox.community.model.Comment;
import work.ubox.community.model.CommentExample;
import work.ubox.community.model.Question;

import java.util.List;

public interface CommentEXTMapper {
    int incCommentCount(Comment comment);
}