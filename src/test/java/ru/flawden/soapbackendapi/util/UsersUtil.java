package ru.flawden.soapbackendapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.flawden.soapbackendapi.endpoint.UsersEndpoint;
import ru.flawden.soapbackendapi.entity.UserEntity;
import ru.flawden.soapbackendapi.repository.UserRepository;
import ru.flawden.soapbackendapi.schema.*;

@Component
public class UsersUtil {

    @Autowired
    UsersEndpoint endpoint;

    @Autowired
    UserRepository repository;

    public final String TEST_LOGIN = "loginlogin";
    public final String TEST_PASSWORD = "Password1";
    public final String TEST_NAME = "Pavel";
    public final String TEST_ROLE = "uSeR";


    public SuccessResponse registerIncorrect(){
        RegisterUserRequest request = new RegisterUserRequest();
        request.setLogin("login");
        request.setPassword("word");
        request.setName("1");
        request.getRole().add("uSeR");
        SuccessResponse response = endpoint.registerUser(request);

        return response;
    }

    public SuccessResponse registerCorrect(){
        RegisterUserRequest request = new RegisterUserRequest();
        request.setLogin(TEST_LOGIN);
        request.setPassword(TEST_PASSWORD);
        request.setName(TEST_NAME);
        request.getRole().add(TEST_ROLE);
        SuccessResponse response = endpoint.registerUser(request);

        return response;
    }

    public SuccessResponse replaceUserToCorrectUser(){
        EditUserRequest request = new EditUserRequest();
        request.setCurrentLogin(TEST_LOGIN);
        request.setNewLogin("loginnew");
        request.setNewPassword("Abrakadabra1");
        request.setNewName("Valeriy");
        request.getNewRole().add("admin");
        request.getNewRole().add("user");
        SuccessResponse response = endpoint.editUser(request);

        return response;
    }

    public SuccessResponse replaceUserToIncorrectUser(){
        EditUserRequest request = new EditUserRequest();
        request.setCurrentLogin(TEST_LOGIN);
        request.setNewLogin("loginnew");
        request.setNewPassword("Abrakadabra1");
        request.setNewName("Valeriy");
        request.getNewRole().add("admin");
        request.getNewRole().add("user");
        SuccessResponse response = endpoint.editUser(request);

        return response;
    }

    public GetUserResponse getUser() {
        GetUserRequest request = new GetUserRequest();
        request.setLogin(TEST_LOGIN);
        request.setPassword(TEST_PASSWORD);

        GetUserResponse response = endpoint.getUser(request);

        return response;
    }

}
