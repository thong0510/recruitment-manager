package fpt.com.fresher.recruitmentmanager.repository;

import fpt.com.fresher.recruitmentmanager.object.entity.SkillCandidate;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillCandidateRepository extends JpaRepository<SkillCandidate, Long> {
}
