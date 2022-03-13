package fpt.com.fresher.recruitmentmanager.service.interfaces;

import fpt.com.fresher.recruitmentmanager.object.entity.Questions;
import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import fpt.com.fresher.recruitmentmanager.object.request.QuestionRequest;
import org.springframework.data.domain.Page;

public interface QuestionService {
    Page<Questions> getAllQuestion(QuestionFilter questionFilter);

    Questions findOne(Long id);

    void updateQuestion(QuestionRequest question);

    void createQuestion(QuestionRequest question);

    void deleteQuestion(Long id);
}
