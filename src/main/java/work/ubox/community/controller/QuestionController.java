package work.ubox.community.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import work.ubox.community.dto.CommentDTO;
import work.ubox.community.dto.QuestionDTO;
import work.ubox.community.enums.CommentTypeEnum;
import work.ubox.community.model.Question;
import work.ubox.community.service.CommentService;
import work.ubox.community.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id")Long id,
                           Model model) {
        QuestionDTO questionDTO = questionService.getById(id);

        List<QuestionDTO> relatedQuestions = questionService.selectedRelated(questionDTO);

        List<CommentDTO> comments =  commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        //累加阅读数
        questionService.incView(id);
        model.addAttribute("question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);

        return "question";
    }
}
