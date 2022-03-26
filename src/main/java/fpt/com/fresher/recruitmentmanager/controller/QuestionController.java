package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.entity.Question;
import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.DifficultyMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuestionMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.TagMapper;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.request.QuestionRequest;
import fpt.com.fresher.recruitmentmanager.object.response.DifficultyResponse;
import fpt.com.fresher.recruitmentmanager.object.response.QuestionResponse;
import fpt.com.fresher.recruitmentmanager.object.response.TagResponse;
import fpt.com.fresher.recruitmentmanager.service.DifficultyServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.QuestionServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.TagServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionServiceImpl questionService;
    private final QuestionMapper questionMapper;
    private final TagMapper tagMapper;
    private final TagServiceImpl tagService;
    private final DifficultyMapper difficultyMapper;
    private final DifficultyServiceImpl difficultyService;

    @ModelAttribute("listTags")
    public List<TagResponse> tags() {
        return tagService.findAllTags().stream().map(tagMapper::entityToTagResponse).collect(Collectors.toList());
    }

    @ModelAttribute("listDifficulties")
    public List<DifficultyResponse> difficulties() {
        return difficultyService.findAllDifficulties().stream().map(difficultyMapper::entityToDifficultyResponse).collect(Collectors.toList());
    }

    @GetMapping("/hr/list-question")
    public String listQuestion(Model model, @RequestParam Optional<String> successMessage,
                               @RequestParam Optional<String> errorMessage,
                               @PathVariable long quizId,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "0") long tagId,
                               @RequestParam(defaultValue = "0") long difficultyId) {

        Pagination pagination = new Pagination();
        pagination.setPageNumber(page - 1);
        QuestionFilter questionFilter = QuestionFilter
                                        .builder()
                                        .difficultyId(difficultyId)
                                        .tagId(tagId)
                                        .pagination(pagination)
                                        .title(search)
                                        .build();

        Page<Question> list = questionService.findAllQuestionsByQuizId(quizId, questionFilter);
        List<QuestionResponse> listQuestionResponse = list.getContent().stream().map(questionMapper::entityToQuestionResponse).collect(Collectors.toList());

        model.addAttribute("listQuestion", listQuestionResponse);
        model.addAttribute("tagIdFilter", tagId);
        model.addAttribute("difficultyIdFilter", difficultyId);
        model.addAttribute("quizId", quizId);
        model.addAttribute("errorMessage", errorMessage.orElse(null));
        model.addAttribute("successMessage", successMessage.orElse(null));
        return "hr/ListQuestionManage";
    }


//    @GetMapping("/hr/detail-question")
//    public String detail(Model model, @RequestParam(name = "id") Long id) {
//        Question question = questionService.findOne(id);
//        QuestionResponse questionResponse = questionMapper.entityToQuestionResponse(question);
//        model.addAttribute("question", questionResponse);
//        return "hr/questionDetail";
//    }
//
//    @GetMapping("/hr/edit-question")
//    public String edit(Model model, @RequestParam Long id) {
//        Question question = questionService.findOne(id);
//        QuestionResponse questionResponse = questionMapper.entityToQuestionResponse(question);
//        model.addAttribute("question", questionResponse);
//        return "hr/editQuestion";
//    }
//
//    @PostMapping("/hr/edit-question")
//    public String edit(@ModelAttribute QuestionRequest question) {
//        questionService.updateQuestion(question);
//        return "redirect:/hr/list-question";
//    }
//
//    @GetMapping("/hr/create-question")
//    public String create(Model model) {
//        QuestionRequest questionRequest = new QuestionRequest();
//        model.addAttribute("question", questionRequest);
//        return "hr/createQuestion";
//    }
//
//    @PostMapping("/hr/create-question")
//    public String create(@ModelAttribute QuestionRequest question) {
//        questionService.createQuestion(question);
//        return "redirect:/hr/list-question";
//    }
//
//    @GetMapping("/hr/delete-question")
//    public String delete(@RequestParam Long id) {
//
//        questionService.deleteQuestion(id);
//        return "redirect:/hr/list-question";
//
//    }

}
