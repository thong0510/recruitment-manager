package fpt.com.fresher.recruitmentmanager.repository;

import fpt.com.fresher.recruitmentmanager.object.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    Optional<Category> findByName(String name);

    @Query(value = "select c.name as name, count(quiz_id) as count from categories c left join (quizzes q join users_quizs uq on q.id = uq.quiz_id) on c.id = q.category_id group by c.name", nativeQuery = true)
    List<CategoryReportMapper> statisticsCategory();

    public static interface CategoryReportMapper {
        String getName();
        Long getCount();
    }
}
