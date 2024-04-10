package eshop.backend.service.impl;

import eshop.backend.exception.UserNotFoundException;
import eshop.backend.model.User;
import eshop.backend.repository.UserRepository;
import eshop.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public User findUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);

        return user
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
    }
}
