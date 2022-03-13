package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.entity.Questions;
import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuestionMapper;
import fpt.com.fresher.recruitmentmanager.object.request.QuestionRequest;
import fpt.com.fresher.recruitmentmanager.object.response.QuestionResponse;
import fpt.com.fresher.recruitmentmanager.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    QuestionMapper questionMapper;

    @GetMapping("/hr/list-question")
    public String listQuestion(Model model) {

        QuestionFilter questionFilter = new QuestionFilter();
        Page<Questions> list = questionService.getAllQuestion(questionFilter);
        List<QuestionResponse> listQuestionResponse = list.getContent().stream().map(questionMapper::entityToQuestionResponse).collect(Collectors.toList());

        model.addAttribute("questions", listQuestionResponse);
        return "hr/listQuestion";
    }


    @GetMapping("/hr/detail-question")
    public String detail(Model model, @RequestParam(name = "id") Long id) {
        Questions question = questionService.findOne(id);
        QuestionResponse questionResponse = questionMapper.entityToQuestionResponse(question);
        model.addAttribute("question", questionResponse);
        return "hr/questionDetail";
    }

    @GetMapping("/hr/edit-question")
    public String edit(Model model, @RequestParam Long id) {
        Questions question = questionService.findOne(id);
        QuestionResponse questionResponse = questionMapper.entityToQuestionResponse(question);
        model.addAttribute("question", questionResponse);
        return "hr/editQuestion";
    }

    @PostMapping("/hr/edit-question")
    public String edit(@ModelAttribute QuestionRequest question) {
        questionService.updateQuestion(question);
        return "redirect:/hr/list-question";
    }

    @GetMapping("/hr/create-question")
    public String create(Model model) {
        QuestionRequest questionRequest = new QuestionRequest();
        model.addAttribute("question", questionRequest);
        return "hr/createQuestion";
    }

    @PostMapping("/hr/create-question")
    public String create(@ModelAttribute QuestionRequest question) {
        questionService.createQuestion(question);
        return "redirect:/hr/list-question";
    }

    @GetMapping("/hr/delete-question")
    public String delete(@RequestParam Long id) {

        questionService.deleteQuestion(id);
        return "redirect:/hr/list-question";

    }

}
