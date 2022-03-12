package fpt.com.fresher.recruitmentmanager.service;

import fpt.com.fresher.recruitmentmanager.object.contant.enums.SystemRole;
import fpt.com.fresher.recruitmentmanager.object.entity.Role;
import fpt.com.fresher.recruitmentmanager.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @PostConstruct
    private void init() {
        if (roleRepository.count()==0) {
            List<Role> systemRoles = new ArrayList<>(Arrays.asList(
                    new Role(SystemRole.ADMIN.getValue()),
                    new Role(SystemRole.HR.getValue()),
                    new Role(SystemRole.INTERVIEWER.getValue()),
                    new Role(SystemRole.USER.getValue())
            ));

            roleRepository.saveAll(systemRoles);
        }


    }
}
