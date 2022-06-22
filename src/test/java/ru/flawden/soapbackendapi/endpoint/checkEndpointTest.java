package ru.flawden.soapbackendapi.endpoint;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.flawden.soapbackendapi.entity.UserEntity;
import ru.flawden.soapbackendapi.exception.UserIsAlreadyExists;
import ru.flawden.soapbackendapi.repository.UserRepository;
import ru.flawden.soapbackendapi.schema.GetUserResponse;
import ru.flawden.soapbackendapi.schema.SuccessResponse;
import ru.flawden.soapbackendapi.util.UsersUtil;

@SpringBootTest
public class checkEndpointTest {

    @Autowired
    UsersUtil util;

    @Autowired
    UserRepository repository;

    @AfterEach
    void setupThis(){
        UserEntity user = repository.findByLogin("loginlogin");
        repository.delete(user);
    }

    @Test
    void tryRegister() {
        SuccessResponse response = util.registerCorrect();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getErrors().size()).isEqualTo(0);
        Assertions.assertThat(response.getSuccess()).isEqualTo("True");
    }

    @Test
    void tryRegisterSecondTimes() {
        try {
            util.registerCorrect();
            util.registerCorrect();
            Assertions.fail("Expected UserIsAlreadyExists");
        } catch (UserIsAlreadyExists e) {
            Assertions.assertThat(e.getMessage()).isNotEqualTo("");
        }
    }

    @Test
    void tryEditUser() {
        util.registerCorrect();
        SuccessResponse response = util.replaceUserToCorrectUser();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getErrors().size()).isEqualTo(0);
        Assertions.assertThat(response.getSuccess()).isEqualTo("True");

    }

    @Test
    void tryGetUser() {
        util.registerCorrect();
        GetUserResponse response = util.getUser();

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getUser()).isNotNull();
        Assertions.assertThat(response.getUser().getLogin()).isEqualTo(util.TEST_LOGIN);
        Assertions.assertThat(response.getUser().getPassword()).isEqualTo(util.TEST_PASSWORD);

    }

}
