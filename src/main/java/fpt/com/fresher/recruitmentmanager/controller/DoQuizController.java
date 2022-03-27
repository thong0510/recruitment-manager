package fpt.com.fresher.recruitmentmanager.controller;

import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import fpt.com.fresher.recruitmentmanager.object.mapper.DifficultyMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuizMapper;
import fpt.com.fresher.recruitmentmanager.object.response.DifficultyResponse;
import fpt.com.fresher.recruitmentmanager.object.response.QuizResponse;
import fpt.com.fresher.recruitmentmanager.service.DifficultyServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.QuizServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class DoQuizController {

    private final QuizServiceImpl quizService;
    private final QuizMapper quizMapper;
    private final DifficultyMapper difficultyMapper;
    private final DifficultyServiceImpl difficultyService;

    @GetMapping("detail/{quizId}")
    String showPage(@PathVariable Long quizId,
                    Model model) {

        QuizResponse quiz = quizMapper.entityToQuizResponse(quizService.findQuizById(quizId));
        List<DifficultyResponse> listOfDifficulty = difficultyService.findAllDifficulties().stream().map(difficultyMapper::entityToDifficultyResponse).toList();
        model.addAttribute("quiz", quiz);
        model.addAttribute("difficulties", listOfDifficulty);
        return "quiz/QuizDetail";

    }

    @GetMapping("start")
    @PreAuthorize("isAuthenticated()")
    public String doQuizByQuizID(@RequestParam String level,
                                 @RequestParam Integer quizId,
                                 Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        int numberOfQuestions = 5;
        model.addAttribute("quizId", quizId);
        model.addAttribute("userId", authentication.getName());
        model.addAttribute("level", level);
        model.addAttribute("numberOfQuestions", numberOfQuestions);
        return "quiz/DoQuiz";
    }
}
