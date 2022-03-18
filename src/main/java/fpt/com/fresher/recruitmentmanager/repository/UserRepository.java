package fpt.com.fresher.recruitmentmanager.repository;

import fpt.com.fresher.recruitmentmanager.object.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {

    boolean existsByUserName(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    Optional<Users> findByEmail(String email);
}
