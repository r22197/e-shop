package eshop.backend.service;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User findUserById(Long id) throws UserNotFoundException;
}
