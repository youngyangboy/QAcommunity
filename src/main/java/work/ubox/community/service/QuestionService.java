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
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;

        Integer totalCount = questionMapper.count();

        totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        page = page < 1 ? 1 : page;
        page = page > totalPage ? totalPage : page;
        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties是Spring提供的一个拷贝对象属性的方法
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totalPage;

        Integer totalCount = questionMapper.countByUserId(userId);

        totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
        page = page < 1 ? 1 : page;
        page = page > totalPage ? totalPage : page;

        paginationDTO.setPagination(totalPage, page);


        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.listByUserId(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //BeanUtils.copyProperties是Spring提供的一个拷贝对象属性的方法
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }


    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);

        QuestionDTO questionDTO = new QuestionDTO();

        BeanUtils.copyProperties(question, questionDTO);

        User user = userMapper.findById(question.getCreator());

        questionDTO.setUser(user);

        return questionDTO;

    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            //如果没有这个问题就创建新的问题
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        } else {
            //否则就进行内容更改
            question.setGmtModified(question.getGmtCreate());
            questionMapper.update(question);
        }
    }
}
