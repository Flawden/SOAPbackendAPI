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
        UserEntity user = userService.findByLoginAndPassword(request.getName(), request.getPassword());
        response.setUser(userService.convertUserToXMLUser(user, true));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
        GetAllUsersResponse response = new GetAllUsersResponse();
        List<UserEntity> users = userService.findAll();
        users.stream().forEach(user -> response.getUser()
                .add(userService.convertUserToXMLUser(user, false)));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "registerUserRequest")
    @ResponsePayload
    public RegisterUserResponse registerUser(@RequestPayload RegisterUserRequest request) {
        RegisterUserResponse response = new RegisterUserResponse();
        userService.saveUser(request);
        response.setSuccess("True");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        DeleteUserResponse response = new DeleteUserResponse();
        userService.delete(request.getLogin());
        response.setSuccess("True");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editUserRequest")
    @ResponsePayload
    public EditUserResponse editUser(@RequestPayload EditUserRequest request) {
        EditUserResponse response = new EditUserResponse();
        userService.edit(request);
        response.setSuccess("True");
        return response;
    }
}
