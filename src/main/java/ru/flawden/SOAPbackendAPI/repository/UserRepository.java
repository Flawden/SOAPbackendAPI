package ru.flawden.SOAPbackendAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flawden.SOAPbackendAPI.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);
    UserEntity findByLoginAndPassword(String username, String password);
}
