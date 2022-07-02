package ru.flawden.soapbackendapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.flawden.soapbackendapi.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByLogin(String login);
    UserEntity findByLoginAndPassword(String username, String password);

}
