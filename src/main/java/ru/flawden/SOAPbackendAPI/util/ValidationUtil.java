package ru.flawden.SOAPbackendAPI.util;

import org.springframework.stereotype.Component;
import ru.flawden.SOAPbackendAPI.entity.UserEntity;
import ru.flawden.SOAPbackendAPI.exception.ValidationException;
import ru.flawden.soapbackendapi.entity.SuccessResponse;

@Component
public class ValidationUtil {

    public boolean registrationValidation(UserEntity user) {
        return validateLogin(user.getLogin())
                && validateName(user.getName())
                && validatePassword(user.getPassword());
    }

    public boolean validateLogin(String login) {
        if(login == null) {
            throw new ValidationException("Parameter \"login\" was not received");
        }
        if((login.length() < 6) && (login.length() >= 20)) {
            throw new ValidationException("Login must contain from 6 to 20 characters");
        }
        if (!login.matches("^[a-zA-Z][a-zA-Z0-9-_\\.]+")) {
            throw new ValidationException("Login must contain only Latin characters " +
                    "and numbers and must not start with a number");
        }
        return true;
    }

    public boolean validateName(String name) {
        if (name == null) {
            throw new ValidationException("Parameter \"name\" was not received");

        }
        if (name.length() < 2) {
            throw new ValidationException("The name must contain at least 2 characters");
        }
        if (!name.matches("[A-Za-z]+")) {
            throw new ValidationException("The name can only contain Latin characters");

        }
        return true;
    }

    public boolean validatePassword(String password) {
        if(password == null) {
            throw new ValidationException("Parameter \"password\" was not received");
        }
        if (password.length() < 6)  {
            throw new ValidationException("Your password must be more than 6 characters");
        }
        if (!password.matches("^(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ])(?=.*\\d)[a-zA-ZА-ЯЁа-яё\\d]+")) {
            throw new ValidationException("Password must contain one capital letter and one number");
        }
        return true;
    }

    public void registrationValidation(UserEntity user, SuccessResponse response) {
         validateLogin(user.getLogin(), response);
         validateName(user.getName(), response);
         validatePassword(user.getPassword(), response);
    }

    public boolean validateLogin(String login, SuccessResponse response) {
        if(login == null) {
            response.getErrors().add("Parameter \\\"login\\\" was not received");
        }
        if (!login.matches("^[a-zA-Z][a-zA-Z0-9-_\\.]{6,20}$")) {
            response.getErrors().add("Login must contain from 6 to 20 characters of the Latin " +
                    "alphabet and begin with a letter");
        }
        return true;
    }

    public void validateName(String name, SuccessResponse response) {
        if (name == null) {
            response.getErrors().add("Parameter \\\"name\\\" was not received");
        }
        if (name.length() < 2) {
            response.getErrors().add("The name must contain at least 2 characters");
        }
        if (!name.matches("[A-Za-z]+")) {
            response.getErrors().add("The name can only contain Latin characters");
        }
    }

    public void validatePassword(String password, SuccessResponse response) {
        if(password == null) {
            response.getErrors().add("Parameter \"password\" was not received");
        }
        if (password.length() < 6)  {
            response.getErrors().add("Your password must be more than 6 characters");
        }
        if (!password.matches("^(?=.*[a-zа-яё])(?=.*[A-ZА-ЯЁ])(?=.*\\d)[a-zA-ZА-ЯЁа-яё\\d]+")) {
            response.getErrors().add("Password must contain one capital letter and one number");
        }
    }

}
