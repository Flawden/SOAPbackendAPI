package ru.flawden.SOAPbackendAPI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.flawden.SOAPbackendAPI.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);
}
