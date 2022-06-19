package ru.flawden.SOAPbackendAPI.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.flawden.SOAPbackendAPI.entity.Role;
import ru.flawden.SOAPbackendAPI.entity.UserEntity;
import ru.flawden.SOAPbackendAPI.repository.UserRepository;
import ru.flawden.soapbackendapi.entity.EditUserRequest;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public UserEntity findByUsernameAndPassword(String username, String password) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public void save(UserEntity user) {
        userRepository.save(user);
    }

    public void delete(String login) {
        UserEntity user = userRepository.findByUsername(login);
        userRepository.delete(user);
    }

    public void edit(EditUserRequest userForEdit) {
        UserEntity user = userRepository.findByUsername(userForEdit.getCurrentLogin());
        if(userForEdit.getNewName() != null) {
            user.setName(userForEdit.getNewName());
        }
        if(userForEdit.getNewLogin() != null) {
            user.setUsername(userForEdit.getNewLogin());
        }
        if(userForEdit.getNewPassword() != null) {
            user.setPassword(userForEdit.getNewPassword());
        }

        if (userForEdit.getNewRole() != null && userForEdit.getNewRole().size() > 0) {
            List<String> rolesForEdit = userForEdit.getNewRole();
            Set<Role> roles = new HashSet<>();
            for (String userRole: rolesForEdit) {
                roles.add(Role.valueOf(userRole));
            }
            user.setRoles(roles);
        }

        userRepository.save(user);
    }
}
