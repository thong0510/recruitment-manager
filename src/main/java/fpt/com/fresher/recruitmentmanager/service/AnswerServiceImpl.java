package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Answer;
import fpt.com.fresher.recruitmentmanager.object.exception.ResourceNotFoundException;
import fpt.com.fresher.recruitmentmanager.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl {

    private final AnswerRepository answerRepository;

    public List<Answer> findAllAnswersByQuestionId(long questionId) {
        return answerRepository.findAllByQuestionId(questionId);
    }

    public Answer findAnswerById(long id) {
        return answerRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found any answer with id " + id));
    }

    public void saveAllAnswer(Set<Answer> answers){
        answerRepository.saveAll(answers);
    }

    public void saveAnswer(Answer answers){
        answerRepository.save(answers);
    }
}
