package fpt.com.fresher.recruitmentmanager.controller;

import fpt.com.fresher.recruitmentmanager.object.entity.Question;
import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import fpt.com.fresher.recruitmentmanager.object.filter.QuizFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.DifficultyMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuestionMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.TagMapper;
import fpt.com.fresher.recruitmentmanager.object.model.Pagination;
import fpt.com.fresher.recruitmentmanager.object.request.AnswerRequest;
import fpt.com.fresher.recruitmentmanager.object.request.QuestionRequest;
import fpt.com.fresher.recruitmentmanager.object.response.DifficultyResponse;
import fpt.com.fresher.recruitmentmanager.object.response.QuestionResponse;
import fpt.com.fresher.recruitmentmanager.object.response.TagResponse;
import fpt.com.fresher.recruitmentmanager.service.DifficultyServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.QuestionServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.TagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @GetMapping("/hr/list-question/{quizId}")
    public String listQuestion(Model model, @RequestParam Optional<String> successMessage,
                               @RequestParam Optional<String> errorMessage,
                               @PathVariable Long quizId,
                               @RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "") String search,
                               @RequestParam(defaultValue = "0") long tagId,
                               @RequestParam(defaultValue = "0") long difficultyId) {

        Pagination pagination = new Pagination();
        pagination.setPageNumber(page - 1);
        pagination.setPageSize(5);
        QuestionFilter questionFilter = QuestionFilter
                                        .builder()
                                        .difficultyId(difficultyId)
                                        .tagId(tagId)
                                        .pagination(pagination)
                                        .title(search)
                                        .build();

        Page<QuestionResponse> list = questionService.findAllQuestionsByQuizId(quizId, questionFilter).map(questionMapper::entityToQuestionResponse);

        model.addAttribute("listQuestion", list);
        model.addAttribute("tagIdFilter", tagId);
        model.addAttribute("difficultyIdFilter", difficultyId);
        model.addAttribute("quizId", quizId);
        model.addAttribute("errorMessage", errorMessage.orElse(null));
        model.addAttribute("successMessage", successMessage.orElse(null));
        return "hr/ListQuestionManage";
    }

    @GetMapping("hr/create-question/{quizId}")
    public String newQuestion(Model model, @PathVariable Long quizId) {
        model.addAttribute("question", new QuestionRequest());
        model.addAttribute("quizId", quizId);
        return "hr/FormQuestionManage";
    }

    @PostMapping("hr/create-question/{quizId}")
    public String createQuestionInQuiz(@PathVariable Long quizId,
                                       @ModelAttribute QuestionRequest questionRequest,
                                       RedirectAttributes redirectAttr) {

        try {
            questionService.createQuestion(questionRequest);
            redirectAttr.addAttribute("successMessage", "Create quiz successfully");
            return "redirect:/hr/list-question/" + quizId;
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttr.addAttribute("errorMessage", "Something wrong, please try again");
            return "redirect:/hr/create-question/" + quizId;
        }

    }

    @GetMapping("hr/edit-question/{questionId}/{quizId}")
    public String GetQuestionInQuiz(Model model,
                                    @PathVariable long quizId,
                                    @PathVariable long questionId,
                                    RedirectAttributes redirectAttr) {

        try {

            QuestionResponse resp = questionMapper.entityToQuestionResponse(questionService.findQuestionById(questionId));

            QuestionRequest questionRequest = new QuestionRequest(resp.getTitle(), resp.getTag().getId(), resp.getDifficulty().getId(), resp.getIsMultiple(), resp.getQuizId());
            resp.getAnswers().forEach(answerResp -> questionRequest.addAnswer(new AnswerRequest(answerResp.getId(), answerResp.getText(), answerResp.getIsCorrect())));

            model.addAttribute("question", questionRequest);
            model.addAttribute("questionInfo", resp);
            model.addAttribute("quizId", quizId);
            return "hr/FormQuestionManage";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttr.addAttribute("errorMessage", "Something wrong, please try again");
            return "redirect:/hr/list-quiz/" + quizId;
        }

    }

    @PostMapping("hr/update-question/{quizId}/{questionId}")
    public String updateQuestion(@PathVariable long quizId,
                                 @PathVariable long questionId,
                                 @ModelAttribute QuestionRequest questionRequest,
                                 RedirectAttributes redirectAttr) {
        try {
            questionService.updateQuestion(questionId, questionRequest);
            redirectAttr.addAttribute("successMessage", "Update quiz successfully");
            return "redirect:/hr/list-question/" + quizId;
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttr.addAttribute("errorMessage", "Something wrong, please try again");
            return "redirect:/admin/quiz/" + quizId + "/question/" + questionId;
        }
    }

    @GetMapping("hr/delete-question/{quizId}/{questionId}")
    public String deleteQuestion(@PathVariable long quizId,
                                 @PathVariable long questionId,
                                 RedirectAttributes redirectAttr) {

        questionService.deleteQuestion(questionId);
        redirectAttr.addAttribute("successMessage", "Delete quiz successfully");

        return "redirect:/hr/list-question/" + quizId;
    }

}
