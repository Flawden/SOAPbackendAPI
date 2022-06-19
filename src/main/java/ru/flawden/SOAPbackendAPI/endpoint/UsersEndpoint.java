package ru.flawden.SOAPbackendAPI.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.flawden.SOAPbackendAPI.entity.Role;
import ru.flawden.SOAPbackendAPI.entity.UserEntity;
import ru.flawden.SOAPbackendAPI.service.UserService;
import ru.flawden.soapbackendapi.entity.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Endpoint
public class UsersEndpoint {

    private static final String NAMESPACE_URI = "http://flawden.ru/SoapBackendApi/entity";

    private UserService userService;

    @Autowired
    public UsersEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        UserEntity user = userService.findByUsernameAndPassword(request.getName(), request.getPassword());
        User xmlUser = new User();
        xmlUser.setPassword(user.getPassword());
        xmlUser.setLogin(user.getLogin());
        xmlUser.setName(user.getName());
        for (Role role: user.getRoles()) {
            xmlUser.getRole().add(role.name());
        }
        response.setUser(xmlUser);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
        GetAllUsersResponse response = new GetAllUsersResponse();
        List<UserEntity> users = userService.findAll();
        List<User> xmlUsers = new ArrayList<>();
        for(UserEntity user: users) {
            User xmlUser = new User();
            xmlUser.setPassword(user.getPassword());
            xmlUser.setLogin(user.getLogin());
            xmlUser.setName(user.getName());
            response.getUser().add(xmlUser);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerUserRequest")
    @ResponsePayload
    public RegisterUserResponse registerUser(@RequestPayload RegisterUserRequest request) {
        RegisterUserResponse response = new RegisterUserResponse();

        UserEntity user = new UserEntity();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        user.setName(request.getName());

        Set<Role> roles = new HashSet<>();
        for (String userRole: request.getRole()) {
            roles.add(Role.valueOf(userRole));
        }
        user.setRoles(roles);
        userService.save(user);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        DeleteUserResponse response = new DeleteUserResponse();
        userService.delete(request.getLogin());
        response.setResult("Success");

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editUserRequest")
    @ResponsePayload
    public EditUserResponse editUser(@RequestPayload EditUserRequest request) {
        EditUserResponse response = new EditUserResponse();
        userService.edit(request);
        response.setResult("Success");

        return response;
    }


}
