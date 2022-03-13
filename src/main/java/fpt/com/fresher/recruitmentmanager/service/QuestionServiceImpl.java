package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Questions;
import fpt.com.fresher.recruitmentmanager.object.filter.QuestionFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.QuestionMapper;
import fpt.com.fresher.recruitmentmanager.object.request.QuestionRequest;
import fpt.com.fresher.recruitmentmanager.repository.QuestionRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.QuestionSpecification;
import fpt.com.fresher.recruitmentmanager.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public Page<Questions> getAllQuestion(QuestionFilter questionFilter) {
        Specification<Questions> specification = QuestionSpecification.getSpecification(questionFilter);
        return questionRepository.findAll(specification, questionFilter.getPagination().getPageAndSort());
    }

    @Override
    public Questions findOne(Long id) {
        return questionRepository.getById(id);
    }

    @Override
    public void updateQuestion(QuestionRequest question) {
        Questions ques = questionMapper.questionRequestToEntity(question);
        questionRepository.save(ques);
    }

    @Override
    public void createQuestion(QuestionRequest question) {
        questionRepository.save(questionMapper.questionRequestToEntity(question));
    }

    @Override
    public void deleteQuestion(Long id) {
        try {
            Questions question = questionRepository.getById(id);

            questionRepository.delete(question);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
