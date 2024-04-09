package eshop.backend.service;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.model.User;

public interface UserService {
    User findUserById(Long id) throws UserNotFoundException;

}
