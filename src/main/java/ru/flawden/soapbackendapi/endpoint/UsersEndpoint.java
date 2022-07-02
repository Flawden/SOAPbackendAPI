package ru.flawden.soapbackendapi.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.flawden.soapbackendapi.entity.*;
import ru.flawden.soapbackendapi.service.UserService;
import ru.flawden.soapbackendapi.schema.*;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class UsersEndpoint {

    private static final String NAMESPACE_URI = "http://flawden.ru/soapbackendapi/schema";

    private UserService userService;

    @Autowired
    public UsersEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        UserEntity user = userService.findByLoginAndPassword(request.getPassword(), request.getLogin());
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

        List<ErrorMessage> errors = new ArrayList<>();
        userService.saveUser(request, errors);

        if(errors.size() > 0) {
            errors.forEach(error -> response.getErrors().add(error.getError()));
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
        List<String> errors = userService.delete(request.getLogin());
        if (errors == null || errors.size() < 1) {
            response.setSuccess("True");
        } else {
            response.getErrors().addAll(errors);
            response.setSuccess("False");
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editUserRequest")
    @ResponsePayload
    public SuccessResponse editUser(@RequestPayload EditUserRequest request) {
        SuccessResponse response = new SuccessResponse();

        List<ErrorMessage> errors = new ArrayList<>();
        userService.edit(request, errors);
        if (errors.size() > 0) {
            errors.forEach(error -> response.getErrors().add(error.getError()));
            response.setSuccess("False");
        } else {
            response.setSuccess("True");
        }
        return response;
    }
}
