package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyLevel;
import fpt.com.fresher.recruitmentmanager.object.entity.*;
import fpt.com.fresher.recruitmentmanager.object.exception.ResourceNotFoundException;
import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.AnswerMapper;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuestionMapper;
import fpt.com.fresher.recruitmentmanager.object.request.AnswerRequest;
import fpt.com.fresher.recruitmentmanager.object.request.QuestionRequest;
import fpt.com.fresher.recruitmentmanager.repository.QuestionRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.QuestionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    private final QuizServiceImpl quizService;
    private final TagServiceImpl tagService;
    private final DifficultyServiceImpl difficultyService;
    private final AnswerServiceImpl answerService;

    public Page<Question> findAllQuestionsByQuizId(long quizId, QuestionFilter filter) {

        Specification<Question> specification;
        if (!ObjectUtils.isEmpty(filter.getLevel()) && DifficultyLevel.RANDOM.equals(filter.getLevel())) {
            filter.setLevel(null);
        }
        specification = QuestionSpecification.getSpecification(quizId, filter);
        return questionRepository.findAll(specification, filter.getPagination().getPageAndSort());
    }

    public Question findQuestionById(long id) {
        return questionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found any question with id " + id));
    }

    public Question createQuestion(QuestionRequest requestBody) {
        convertAnswerTextToAnswersReq(requestBody);
        Question question = questionMapper.questionRequestToEntity(requestBody);
        updateRelationProperties(question, requestBody);

        return question;
    }

    private void convertAnswerTextToAnswersReq(QuestionRequest questionRequest) {

        for (int i = 0; i < questionRequest.getAnswersText().size(); i++) {

            String answerText = questionRequest.getAnswersText().get(i);
            Long answerId = null;
            boolean isCorrect = false;
            if (!answerText.isEmpty()) {
                if (questionRequest.getAnswersId() != null) {
                    if (questionRequest.getAnswersId().size() - 1 >= i) {
                        answerId = questionRequest.getAnswersId().get(i);
                    }
                }

                for (Integer value : questionRequest.getAnswersIsCorrect()) {
                    isCorrect = value == (i + 1);
                    if (isCorrect) break;
                }
                questionRequest.getAnswers().add(new AnswerRequest(answerId, answerText, isCorrect));
            }
        }

    }

    public Question updateQuestion(long id, QuestionRequest requestBody) {
        Question question = this.findQuestionById(id);
        convertAnswerTextToAnswersReq(requestBody);
        questionMapper.updateEntity(question, requestBody);
        updateRelationProperties(question, requestBody);

        return question;
    }

    public void deleteQuestion(long id) {
        Question question = this.findQuestionById(id);

        questionRepository.delete(question);
    }

    private void updateRelationProperties(Question question, QuestionRequest requestBody) {
        Quiz quiz = quizService.findQuizById(requestBody.getQuizId());
        question.setQuiz(quiz);

        Tag tag = tagService.findTagById(requestBody.getTagId());
        question.setTag(tag);

        Difficulty difficulty = difficultyService.findDifficultyById(requestBody.getDifficultyId());
        question.setDifficulty(difficulty);

        Set<Answer> answers = requestBody.getAnswers().stream()
                .map(answerMapper::answerRequestToEntity)
                .collect(Collectors.toSet());

        if (question.getId() != null) {
            answers.forEach(answer -> answer.setQuestion(question));
        } else {
            Question question1 = questionRepository.save(question);
            answers.forEach(answer -> answer.setQuestion(question1));
        }
        answerService.saveAllAnswer(answers);
    }
}
