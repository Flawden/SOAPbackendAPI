package ru.flawden.soapbackendapi.service;

import org.springframework.stereotype.Service;
import ru.flawden.soapbackendapi.entity.ErrorMessage;
import ru.flawden.soapbackendapi.entity.Role;
import ru.flawden.soapbackendapi.entity.UserEntity;
import ru.flawden.soapbackendapi.exception.UserDoesNotExistException;
import ru.flawden.soapbackendapi.exception.UserIsAlreadyExists;
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

    public void saveUser(RegisterUserRequest request, List<ErrorMessage> errors) {

        UserEntity user = new UserEntity();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        Set<String> rolesList = Set.copyOf(request.getRole());

        if(rolesList.isEmpty()) {
            errors.add(new ErrorMessage("The list of roles cannot be empty"));
        }
        if(userRepository.findByLogin(user.getLogin()) != null) {
            errors.add(new ErrorMessage("User with login \"" + user.getLogin() + "\" already exists"));
        }

        validationUtil.registrationValidation(user, errors);

        if (errors.size() > 0) {
            return;
        }

        Set<Role> roles = new HashSet<>();
        for (String role: rolesList) {
            try{
                roles.add(Role.valueOf(role.toUpperCase(Locale.ROOT)));
            } catch (IllegalArgumentException e) {
                errors.add(new ErrorMessage("The role value \"" + role + "\" is not in " +
                        "the list of possible roles. Check spelling"));
            }
        }

        if(errors.size() < 1) {
            user.setRoles(roles);
            userRepository.save(user);
        }
    }

    public List<String> delete(String login) {
        ArrayList<String> errors = new ArrayList<>();
        UserEntity user = userRepository.findByLogin(login);
        if (user != null) {
            userRepository.delete(user);
        } else {
            errors.add("User \"" + login + "\" does not exist");
        }

        return errors;
    }

    public void edit(EditUserRequest request, List<ErrorMessage> errors) {
        UserEntity userForEdit = new UserEntity();
        userForEdit.setPassword(request.getPassword());
        userForEdit.setName(request.getName());
        Set<String> rolesForEdit = Set.copyOf(request.getRole());
        String login = request.getCurrentLogin();

        UserEntity user = userRepository.findByLogin(login);
        if(user == null) {
            throw new UserIsAlreadyExists("User with login \"" + login + "\" does not exist");
        }
        if (userForEdit.getName() != null) {
            validationUtil.validateName(userForEdit.getName(), errors);
            user.setName(user.getName());
        }
        if (userForEdit.getLogin() != null) {
            validationUtil.validateLogin(userForEdit.getLogin(), errors);
            user.setLogin(user.getLogin());
        }
        if (userForEdit.getPassword() != null) {
            validationUtil.validatePassword(userForEdit.getPassword(), errors);
            user.setPassword(user.getPassword());
        }

        if (rolesForEdit != null && rolesForEdit.size() > 0) {
            Set<Role> roles = new HashSet<>();
            for (String userRole : rolesForEdit) {
                try{
                    roles.add(Role.valueOf(userRole.toUpperCase(Locale.ROOT)));
                } catch (IllegalArgumentException e) {
                    errors.add(new ErrorMessage("The role value \"" + userRole + "\" is not in " +
                            "the list of possible roles. Check spelling"));
                }
            }
            if (roles.size() > 0) {
                user.setRoles(roles);
            }
        }
        if (errors.size() < 1) {
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
