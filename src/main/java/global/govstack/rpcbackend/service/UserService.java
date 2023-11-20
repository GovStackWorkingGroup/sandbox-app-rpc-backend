package global.govstack.rpcbackend.service;

import global.govstack.rpcbackend.dto.SignUpDto;
import global.govstack.rpcbackend.model.Role;
import global.govstack.rpcbackend.model.User;
import global.govstack.rpcbackend.repository.RoleRepository;
import global.govstack.rpcbackend.repository.UserRepository;
import global.govstack.rpcbackend.service.exception.role.RoleDoNotExistsException;
import global.govstack.rpcbackend.service.exception.user.UserDoNotExistsException;
import global.govstack.rpcbackend.service.exception.user.UserExistsException;
import java.util.Collections;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  private final RoleRepository roleRepository;

  public UserService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.roleRepository = roleRepository;
  }

  public User loadUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UserDoNotExistsException("User do not exist!"));
  }

  public User addNewUser(SignUpDto signUpDto, String role) {
    if (userRepository.existsByUsername(signUpDto.getUsername())) {
      throw new UserExistsException("Username is already taken!");
    }

    User user = new User();
    user.setName(signUpDto.getName());
    user.setUsername(signUpDto.getUsername());
    user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

    Role roles =
        roleRepository
            .findByName(role)
            .orElseThrow(() -> new RoleDoNotExistsException("Role do not exist!"));
    user.setRoles(Collections.singleton(roles));

    return userRepository.save(user);
  }
}
