package ru.flawden.SOAPbackendAPI.repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.flawden.SOAPbackendAPI.entity.UserEntity;

@Component
public class AlterUserRepository {
    private static final Map<String, UserEntity> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        UserEntity spain = new UserEntity();
        spain.setUsername("Spain");
        spain.setEmail("Spain@mail.ru");
        spain.setPassword("password");

        countries.put(spain.getUsername(), spain);
    }

    public UserEntity findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }
}
