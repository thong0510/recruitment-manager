package fpt.com.fresher.recruitmentmanager.repository;

import fpt.com.fresher.recruitmentmanager.object.entity.UserQuiz;
import fpt.com.fresher.recruitmentmanager.object.entity.UserQuizId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizRepository extends JpaRepository<UserQuiz, UserQuizId>, JpaSpecificationExecutor<UserQuiz> {
    @Query(value = "select * from users_quizs uq where uq.user_name = :username order by uq.recent_active_date desc limit 10", nativeQuery = true)
    List<UserQuiz> findTop10ByUserOrderByRecentActiveDateDesc(String username);
}
