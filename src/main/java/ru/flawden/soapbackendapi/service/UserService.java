package ru.flawden.soapbackendapi.service;

import org.springframework.stereotype.Service;
import ru.flawden.soapbackendapi.entity.Role;
import ru.flawden.soapbackendapi.entity.UserEntity;
import ru.flawden.soapbackendapi.exception.UserDoesNotExistException;
import ru.flawden.soapbackendapi.exception.ValidationException;
import ru.flawden.soapbackendapi.repository.UserRepository;
import ru.flawden.soapbackendapi.schema.*;
import ru.flawden.soapbackendapi.util.ValidationUtil;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    public UserService(UserRepository userRepository, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.validationUtil = validationUtil;
    }

    public UserEntity findByLoginAndPassword(String password, String login) {
        validationUtil.validatePassword(password);
        validationUtil.validateLogin(login);
        UserEntity user = userRepository.findByLoginAndPassword(login, password);

        if(user == null) {
            throw new UserDoesNotExistException("There is no user with this username and password");
        }

        return user;
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void saveUser(RegisterUserRequest request, SuccessResponse response) {
        UserEntity user = new UserEntity();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setName(request.getName());

        validationUtil.registrationValidation(user, response);

        if (response.getErrors().size() < 1 || response.getErrors() == null) {
            if(request.getRole().isEmpty() || request.getRole() == null) {
                response.getErrors().add("The list of roles cannot be empty");
            } else {
                Set<Role> roles = new HashSet<>();
                for (String userRole: request.getRole()) {
                    try{
                        roles.add(Role.valueOf(userRole.toUpperCase(Locale.ROOT)));
                    } catch (IllegalArgumentException e) {
                        response.getErrors().add("The role value \"" + userRole + "\" is not in " +
                                "the list of possible roles. Check spelling");
                    }
                }
                if(response.getErrors().size() < 1 || response.getErrors() == null) {
                    user.setRoles(roles);
                    userRepository.save(user);
                }
            }
        }
    }

    public void delete(String login, SuccessResponse response) {
        UserEntity user = userRepository.findByLogin(login);
        if (user != null) {
            userRepository.delete(user);
        } else {
            response.getErrors().add("User \"" + login + "\" does not exist");
        }

    }

    public void edit(EditUserRequest userForEdit, SuccessResponse response) {
        UserEntity user = userRepository.findByLogin(userForEdit.getCurrentLogin());
        if (userForEdit.getNewName() != null) {
            validationUtil.validateName(userForEdit.getNewName(), response);
            user.setName(userForEdit.getNewName());
        }
        if (userForEdit.getNewLogin() != null) {
            validationUtil.validateLogin(userForEdit.getNewLogin(), response);
            user.setLogin(userForEdit.getNewLogin());
        }
        if (userForEdit.getNewPassword() != null) {
            validationUtil.validatePassword(userForEdit.getNewPassword(), response);
            user.setPassword(userForEdit.getNewPassword());
        }
        if (userForEdit.getNewRole() != null && userForEdit.getNewRole().size() > 0) {
            List<String> rolesForEdit = userForEdit.getNewRole();
            Set<Role> roles = new HashSet<>();
            for (String userRole : rolesForEdit) {
                try{
                    roles.add(Role.valueOf(userRole.toUpperCase(Locale.ROOT)));
                } catch (IllegalArgumentException e) {
                    response.getErrors().add("The role value \"" + userRole + "\" is not in " +
                            "the list of possible roles. Check spelling");
                }
            }
            if (roles.size() > 0) {
                user.setRoles(roles);
            }
        }
        if (response.getErrors() == null || response.getErrors().size() < 1) {
            userRepository.save(user);
        }
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
