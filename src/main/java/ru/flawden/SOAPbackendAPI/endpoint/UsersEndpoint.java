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
    public SuccessResponse registerUser(@RequestPayload RegisterUserRequest request) {
        SuccessResponse response = new SuccessResponse();
        userService.saveUser(request, response);
        if(response.getErrors().size() > 0) {
            response.setSuccess("False");
            return response;
        } else {
            response.setSuccess("True");
            return response;
        }

    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public SuccessResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        SuccessResponse response = new SuccessResponse();
        userService.delete(request.getLogin(), response);
        if (response.getErrors() == null || response.getErrors().size() < 1) {
            response.setSuccess("True");
        } else {
            response.setSuccess("False");
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editUserRequest")
    @ResponsePayload
    public SuccessResponse editUser(@RequestPayload EditUserRequest request) {
        SuccessResponse response = new SuccessResponse();
        userService.edit(request, response);
        if (response.getErrors() == null || response.getErrors().size() < 1) {
            response.setSuccess("True");
        } else {
            response.setSuccess("False");
        }
        return response;
    }
}
