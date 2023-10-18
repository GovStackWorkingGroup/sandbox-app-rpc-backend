package global.govstack.rpcbackend.service;

import global.govstack.rpcbackend.model.User;
import global.govstack.rpcbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User loadUserByUsername(String username) {
    return userRepository.findByUsername(username).get();
  }
}
