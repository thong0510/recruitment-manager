package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyLevel;
import fpt.com.fresher.recruitmentmanager.object.contant.QuizStatus;
import fpt.com.fresher.recruitmentmanager.object.entity.*;
import fpt.com.fresher.recruitmentmanager.object.exception.ResourceNotFoundException;
import fpt.com.fresher.recruitmentmanager.object.filter.QuizFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuizMapper;
import fpt.com.fresher.recruitmentmanager.object.request.QuizRequest;
import fpt.com.fresher.recruitmentmanager.object.response.QuizResponse;
import fpt.com.fresher.recruitmentmanager.repository.QuizRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.QuizSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl {

    private final QuizRepository quizRepository;
    private final QuizMapper quizMapper;

    private final CategoryServiceImpl categoryService;
    private final UserServiceImpl userService;
    private final TagServiceImpl tagService;
    private final DifficultyServiceImpl difficultyService;
    private final CloudinaryService cloudinaryService;

    public Page<Quiz> findAllQuizzes(QuizFilter filter) {
        Specification<Quiz> specification = QuizSpecification.getSpecification(filter);
        return quizRepository.findAll(specification, filter.getPagination().getPageAndSort());
    }

    public Quiz findQuizById(long id) {
        return quizRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found any quiz with id " + id));
    }

    public Quiz createQuiz(QuizRequest requestBody) {
        Quiz quiz = quizMapper.quizRequestToEntity(requestBody);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users instructor = (Users) authentication.getPrincipal();
        quiz.setInstructor(instructor);

        Category category = categoryService.findCategoryById(requestBody.getCategoryId());
        quiz.setCategory(category);

        if (!ObjectUtils.isEmpty(requestBody.getImageFile())) {
            String image = cloudinaryService.uploadImage(null, requestBody.getImageFile());
            if (image != null) quiz.setImage(image);
        }

        return quizRepository.save(quiz);
    }

    public Quiz updateQuiz(long id, QuizRequest requestBody) {
        System.out.println("update quiz: "+requestBody);
        Quiz quiz = this.findQuizById(id);
        quizMapper.updateEntity(quiz, requestBody);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users instructor = (Users) authentication.getPrincipal();
        quiz.setInstructor(instructor);

        Category category = categoryService.findCategoryById(requestBody.getCategoryId());
        quiz.setCategory(category);

        if (!requestBody.getImageFile().isEmpty()) {
            String image = cloudinaryService.uploadImage(quiz.getImage(), requestBody.getImageFile());
            if (image != null) quiz.setImage(image);
        }

        return quizRepository.save(quiz);
    }

    public void deleteQuiz(long id) {
        Quiz quiz = this.findQuizById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Users instructor = (Users) authentication.getPrincipal();

        quizRepository.delete(quiz);
    }

    public void changeQuizStatus(long id, String status) {
        Quiz quiz = this.findQuizById(id);

        quiz.setStatus(Enum.valueOf(QuizStatus.class, status));

        quizRepository.save(quiz);
    }

    @PostConstruct
    private void init() {
        if (quizRepository.count() == 0) {
            Quiz initialQuiz = new Quiz();
            initialQuiz.setTitle("Elementary HTML/CSS for beginner");
            initialQuiz.setStatus(QuizStatus.PUBLIC);
            initialQuiz.setDescription("Basic HTML/CSS for...");
            initialQuiz.setCategory(categoryService.findCategoryByName("Development"));
            initialQuiz.setImage(
                    "https://res.cloudinary.com/fpt-software-quiz/image/upload/v1644700398/html-css"
                            + "-course_n8stxa.jpg");

            Tag htmlCssTag = tagService.findTagByName("HTML5/CSS3");

            Question question1 = new Question();
            question1.setTag(htmlCssTag);
            question1.setDifficulty(difficultyService.findDifficultyByLevel(DifficultyLevel.EASY));
            question1.setIsMultiple(false);
            question1.setTitle("What does CSS stand for?");
            List<Answer> answersForQuestion1 = Arrays.asList(
                    new Answer("Creative Style Sheet", false),
                    new Answer("Cascading Style Sheet", true),
                    new Answer("Colorful Style Sheet", false),
                    new Answer("Computer Style Sheet", false)
            );
            answersForQuestion1.forEach(question1::addAnswer);


            Question question2 = new Question();
            question2.setTag(htmlCssTag);
            question2.setDifficulty(difficultyService.findDifficultyByLevel(DifficultyLevel.EASY));
            question2.setIsMultiple(false);
            question2.setTitle("Which CSS property is used to change the text color of an element?");
            List<Answer> answersForQuestion2 = Arrays.asList(
                    new Answer("color", true),
                    new Answer("text-color", false),
                    new Answer("fg-color", false)
            );
            answersForQuestion2.forEach(question2::addAnswer);

            List<Question> initialQuestions = Arrays.asList(question1, question2);
            initialQuestions.forEach(initialQuiz::addQuestion);

            quizRepository.save(initialQuiz);
        }
    }
}
