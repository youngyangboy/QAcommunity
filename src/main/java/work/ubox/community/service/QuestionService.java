package work.ubox.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import work.ubox.community.dto.PaginationDTO;
import work.ubox.community.dto.QuestionDTO;
import work.ubox.community.mapper.QuestionMapper;
import work.ubox.community.mapper.UserMapper;
import work.ubox.community.model.Question;
import work.ubox.community.model.User;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    public PaginationDTO list(Integer page, Integer size) {
        Integer totalCount = questionMapper.count();
        PaginationDTO paginationDTO = new PaginationDTO();

        paginationDTO.setPagination(totalCount, page, size);

        page = page<1?1:page;

        page = page>paginationDTO.getTotalPage()?paginationDTO.getTotalPage():page;

        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties是Spring提供的一个拷贝对象属性的方法
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
