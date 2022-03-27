package fpt.com.fresher.recruitmentmanager.repository;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.SystemRole;
import fpt.com.fresher.recruitmentmanager.object.entity.Role;
import fpt.com.fresher.recruitmentmanager.object.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRole(SystemRole role);

}
