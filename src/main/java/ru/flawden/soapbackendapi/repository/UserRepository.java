package ru.flawden.soapbackendapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flawden.soapbackendapi.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);
    UserEntity findByLoginAndPassword(String username, String password);
}
