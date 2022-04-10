package work.ubox.community.mapper;

import work.ubox.community.model.Question;

public interface QuestionEXTMapper {
    int incView(Question record);

    int incCommentCount(Question record);
}