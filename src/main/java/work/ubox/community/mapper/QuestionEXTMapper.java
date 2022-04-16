package work.ubox.community.mapper;

import work.ubox.community.model.Question;

import java.util.List;

public interface QuestionEXTMapper {
    int incView(Question record);

    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);
}