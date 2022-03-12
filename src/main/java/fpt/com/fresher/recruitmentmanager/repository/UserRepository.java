package fpt.com.fresher.recruitmentmanager.repository;

import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {
}
