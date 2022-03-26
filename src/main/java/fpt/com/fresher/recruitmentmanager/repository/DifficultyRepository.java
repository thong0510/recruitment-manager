package fpt.com.fresher.recruitmentmanager.repository;

import fpt.com.fresher.recruitmentmanager.object.contant.DifficultyLevel;
import fpt.com.fresher.recruitmentmanager.object.entity.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {

    Optional<Difficulty> findByLevel(DifficultyLevel level);
}
