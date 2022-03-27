package fpt.com.fresher.recruitmentmanager.controller.api;

import fpt.com.fresher.recruitmentmanager.service.QuizServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quizzes")
@RequiredArgsConstructor
@CrossOrigin("*")
public class QuizControllerApi {

    private final QuizServiceImpl quizService;

    @PatchMapping(value = "/{id}/{status}")
    ResponseEntity<?> changeQuizStatus(@PathVariable("id") Long id, @PathVariable("status") String status) {
        quizService.changeQuizStatus(id, status);

        return ResponseEntity.ok().build();
    }
}
