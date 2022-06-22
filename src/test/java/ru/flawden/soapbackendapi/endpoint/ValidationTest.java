package ru.flawden.soapbackendapi.endpoint;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.flawden.soapbackendapi.entity.Role;
import ru.flawden.soapbackendapi.entity.UserEntity;
import ru.flawden.soapbackendapi.repository.UserRepository;
import ru.flawden.soapbackendapi.schema.RegisterUserRequest;
import ru.flawden.soapbackendapi.schema.SuccessResponse;
import ru.flawden.soapbackendapi.util.UsersUtil;

import java.util.Collections;

@SpringBootTest
public class ValidationTest {

    @Autowired
    UsersEndpoint endpoint;

    @Autowired
    UserRepository repository;

    @Autowired
    UsersUtil util;

    @Test
    void tryRegisterIncorrectUser() {

        SuccessResponse response = util.registerIncorrect();

        Assertions.assertThat(response.getErrors().contains("Login must contain from 6 to 20 characters of the Latin alphabet and begin with a letter")).isEqualTo(true);
        Assertions.assertThat(response.getErrors().contains("The name must contain at least 2 characters")).isEqualTo(true);
        Assertions.assertThat(response.getErrors().contains("The name can only contain Latin characters")).isEqualTo(true);
        Assertions.assertThat(response.getErrors().contains("Your password must be more than 6 characters")).isEqualTo(true);
        Assertions.assertThat(response.getErrors().contains("Password must contain one capital letter and one number")).isEqualTo(true);
        Assertions.assertThat(response.getSuccess()).isEqualTo("False");
    }

    @Test
    void tryEditIncorrectUser() {

        util.registerCorrect();
        SuccessResponse response = util.registerIncorrect();

        Assertions.assertThat(response.getErrors().contains("Login must contain from 6 to 20 characters of the Latin alphabet and begin with a letter")).isEqualTo(true);
        Assertions.assertThat(response.getErrors().contains("The name must contain at least 2 characters")).isEqualTo(true);
        Assertions.assertThat(response.getErrors().contains("The name can only contain Latin characters")).isEqualTo(true);
        Assertions.assertThat(response.getErrors().contains("Your password must be more than 6 characters")).isEqualTo(true);
        Assertions.assertThat(response.getErrors().contains("Password must contain one capital letter and one number")).isEqualTo(true);
        Assertions.assertThat(response.getSuccess()).isEqualTo("False");
    }

}
