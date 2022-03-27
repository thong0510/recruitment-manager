package fpt.com.fresher.recruitmentmanager.controller.api;

import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuestionMapper;
import fpt.com.fresher.recruitmentmanager.object.request.QuestionRequest;
import fpt.com.fresher.recruitmentmanager.object.response.QuestionResponse;
import fpt.com.fresher.recruitmentmanager.service.QuestionServiceImpl;
import fpt.com.fresher.recruitmentmanager.service.interfaces.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/questions")
@RequiredArgsConstructor
@CrossOrigin("*")
public class QuestionApiController {

    private final QuestionServiceImpl questionService;
    private final QuestionMapper questionMapper;

    @PostMapping(value = "/{quizId}/all")
    ResponseEntity<?> getQuestionsByQuizIdWithFilter(@PathVariable Long quizId,
                                                     @RequestBody Optional<QuestionFilter> filter) {

        Page<QuestionResponse> responses =
                questionService.findAllQuestionsByQuizId(quizId, filter.orElse(new QuestionFilter()))
                        .map(questionMapper::entityToQuestionResponse);

        return ResponseEntity.ok(responses);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<?> getQuizById(@PathVariable("id") Long id) {
        QuestionResponse response = questionMapper.entityToQuestionResponse(questionService.findQuestionById(id));
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @PostMapping
    ResponseEntity<?> addQuestionToQuiz(@RequestBody @Valid QuestionRequest requestBody) {
        QuestionResponse response =
                questionMapper.entityToQuestionResponse(questionService.createQuestion(requestBody));
        return ResponseEntity.ok(response);
    }


    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @PutMapping(value = "/{id}")
    ResponseEntity<?> updateQuestionInQuiz(@PathVariable Long id,
                                           @RequestBody @Valid QuestionRequest requestBody) {
        QuestionResponse response =
                questionMapper.entityToQuestionResponse(questionService.updateQuestion(id, requestBody));

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'INSTRUCTOR')")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<?> deleteQuestionFromQuiz(@PathVariable Long id) {
        questionService.deleteQuestion(id);

        return ResponseEntity.ok().build();
    }
}
