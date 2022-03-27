package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.entity.Quiz;
import fpt.com.fresher.recruitmentmanager.object.entity.UserQuiz;
import fpt.com.fresher.recruitmentmanager.object.entity.UserQuizId;
import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import fpt.com.fresher.recruitmentmanager.object.exception.ResourceNotFoundException;
import fpt.com.fresher.recruitmentmanager.object.filter.UserQuizFilter;
import fpt.com.fresher.recruitmentmanager.object.mapper.UserQuizMapper;
import fpt.com.fresher.recruitmentmanager.object.request.UserQuizRequest;
import fpt.com.fresher.recruitmentmanager.repository.UserQuizRepository;
import fpt.com.fresher.recruitmentmanager.repository.UserRepository;
import fpt.com.fresher.recruitmentmanager.repository.spec.UserQuizSpecification;
import fpt.com.fresher.recruitmentmanager.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserQuizHistoryService {

    private final UserQuizRepository userQuizRepository;
    private final UserRepository userRepository;
    private final QuizServiceImpl quizService;
    private final UserQuizMapper userQuizMapper;

    public List<UserQuiz> findQuizRecent(String username) {

        return userQuizRepository.findTop10ByUserOrderByRecentActiveDateDesc(username);
    }

    public Page<UserQuiz> findAllUserQuizzes(UserQuizFilter filter) {
        Specification<UserQuiz> specification = UserQuizSpecification.getSpecification(filter);
        return userQuizRepository.findAll(specification, filter.getPagination().getPageAndSort());
    }

    public UserQuiz findUserQuizById(UserQuizId id) {
        return userQuizRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found any quiz with id " + id));
    }

//    public UserQuiz createUserQuiz(UserQuizRequest requestBody) {
//
//        UserQuiz userQuiz = userQuizMapper.userQuizRequestToEntity(requestBody);
//
//        Optional<UserQuiz> checked = userQuizRepository.findById(userQuiz.getId());
//
//        if (!checked.isPresent()) {
//
//            Optional<Users> user = userRepository.findById(userQuiz.getId().getUserId());
//            Quiz quiz = quizService.findQuizById(userQuiz.getId().getQuizId());
//
//            userQuiz.setUser(user.orElse(null));
//            userQuiz.setQuiz(quiz);
//            return userQuizRepository.save(userQuiz);
//
//        } else {
//            if (checked.get().getMaxScore() < userQuiz.getMaxScore() ||
//                    Objects.equals(checked.get().getMaxScore(), userQuiz.getMaxScore())
//                            && checked.get().getRemainingTime() > userQuiz.getRemainingTime()) {
//
//                userQuizMapper.updateEntity(checked.get(), requestBody);
//                return userQuizRepository.save(checked.get());
//            }
//        }
//        return userQuiz;
//
//    }

    public UserQuiz updateUserQuiz(UserQuizRequest requestBody) {

        UserQuiz request = userQuizMapper.userQuizRequestToEntity(requestBody);
        UserQuiz userQuiz = this.findUserQuizById(request.getId());

        userQuizMapper.updateEntity(userQuiz, requestBody);

        return userQuizRepository.save(userQuiz);
    }

    public void deleteQuiz(UserQuizId id) {
        UserQuiz userQuiz = this.findUserQuizById(id);
        userQuizRepository.delete(userQuiz);
    }

    public int findSize() {
        return userQuizRepository.findAll().size();
    }
}
