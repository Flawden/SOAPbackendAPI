package ru.flawden.SOAPbackendAPI.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.flawden.SOAPbackendAPI.entity.UserEntity;
import ru.flawden.SOAPbackendAPI.service.UserService;
import ru.flawden.soapbackendapi.entity.GetUserRequest;
import ru.flawden.soapbackendapi.entity.GetUserResponse;
import ru.flawden.soapbackendapi.entity.User;

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
    public GetUserResponse getCountry(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        UserEntity user = userService.findByUsername(request.getName());
        System.out.println(user + " IT'S USER!!!!!!");
        User xmlUser = new User();
        xmlUser.setPassword(user.getPassword());
        xmlUser.setEmail(user.getEmail());
        xmlUser.setUsername(user.getUsername());
        response.setUser(xmlUser);
        return response;
    }

}
