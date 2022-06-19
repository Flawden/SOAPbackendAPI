package ru.flawden.SOAPbackendAPI.service;

import org.springframework.stereotype.Service;
import ru.flawden.SOAPbackendAPI.entity.Role;
import ru.flawden.SOAPbackendAPI.entity.UserEntity;
import ru.flawden.SOAPbackendAPI.repository.UserRepository;
import ru.flawden.SOAPbackendAPI.util.ValidationUtil;
import ru.flawden.soapbackendapi.entity.EditUserRequest;
import ru.flawden.soapbackendapi.entity.RegisterUserRequest;
import ru.flawden.soapbackendapi.entity.User;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    public UserService(UserRepository userRepository, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
    }

    public UserEntity findByUsernameAndPassword(String username, String password) {
        return userRepository.findByLoginAndPassword(username, password);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(RegisterUserRequest request) {
        UserEntity user = new UserEntity();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setName(request.getName());

        validationUtil.registrationValidation(user);

        Set<Role> roles = new HashSet<>();
        for (String userRole: request.getRole()) {
            roles.add(Role.valueOf(userRole.toUpperCase(Locale.ROOT)));
        }
        user.setRoles(roles);
        userRepository.save(user);
    }

    public void delete(String login) {
        UserEntity user = userRepository.findByLogin(login);
        userRepository.delete(user);
    }

    public void edit(EditUserRequest userForEdit) {
        UserEntity user = userRepository.findByLogin(userForEdit.getCurrentLogin());
        if (userForEdit.getNewName() != null) {
            user.setName(userForEdit.getNewName());
        }
        if (userForEdit.getNewLogin() != null) {
            user.setLogin(userForEdit.getNewLogin());
        }
        if (userForEdit.getNewPassword() != null) {
            user.setPassword(userForEdit.getNewPassword());
        }
        if (userForEdit.getNewRole() != null && userForEdit.getNewRole().size() > 0) {
            List<String> rolesForEdit = userForEdit.getNewRole();
            Set<Role> roles = new HashSet<>();
            for (String userRole : rolesForEdit) {
                roles.add(Role.valueOf(userRole));
            }
            user.setRoles(roles);
        }
        userRepository.save(user);
    }

    public User convertUserToXMLUser(UserEntity user, boolean isRoleNeeded) {
        User xmlUser = new User();
        xmlUser.setPassword(user.getPassword());
        xmlUser.setLogin(user.getLogin());
        xmlUser.setName(user.getName());
        if(isRoleNeeded) {
            for (Role role : user.getRoles()) {
                xmlUser.getRole().add(role.name());
            }
        }
        return xmlUser;
    }
}
