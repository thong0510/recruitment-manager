package fpt.com.fresher.recruitmentmanager.controller.api;

import fpt.com.fresher.recruitmentmanager.object.filter.QuizFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuizMapper;
import fpt.com.fresher.recruitmentmanager.object.response.QuizResponse;
import fpt.com.fresher.recruitmentmanager.service.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/quizzes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class QuizControllerApi {

    private final QuizServiceImpl quizService;
    private final QuizMapper quizMapper;

    @PatchMapping(value = "/{id}/{status}")
    ResponseEntity<?> changeQuizStatus(@PathVariable("id") Long id, @PathVariable("status") String status) {
        quizService.changeQuizStatus(id, status);

        return ResponseEntity.ok().build();
    }

    @PostMapping("all")
    ResponseEntity<?> getAllQuizzes(@RequestBody Optional<QuizFilter> filter) {
        Page<QuizResponse> responses = quizService.findAllQuizzes(filter.orElse(new QuizFilter()))
                .map(quizMapper::entityToQuizResponse);

        return ResponseEntity.ok(responses);

    }
}
