package global.govstack.rpcbackend.controller;

import global.govstack.rpcbackend.dto.LoginDto;
import global.govstack.rpcbackend.dto.SignUpDto;
import global.govstack.rpcbackend.model.Role;
import global.govstack.rpcbackend.model.User;
import global.govstack.rpcbackend.repository.RoleRepository;
import global.govstack.rpcbackend.repository.UserRepository;
import global.govstack.rpcbackend.service.CustomUserDetailsService;
import global.govstack.rpcbackend.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired private CustomUserDetailsService service;

  @Autowired private JwtUtils jwtUtils;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Operation(summary = "Register user.")
  @SecurityRequirements
  @PostMapping("/register")
  public ResponseEntity<String> addNewUser(@RequestBody SignUpDto signUpDto) {

    if (userRepository.existsByUsername(signUpDto.getUsername())) {
      return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
    }

    User user = new User();
    user.setName(signUpDto.getName());
    user.setUsername(signUpDto.getUsername());
    user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

    Role roles = roleRepository.findByName("ROLE_USER").get();
    user.setRoles(Collections.singleton(roles));

    userRepository.save(user);

    return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
  }

  @Operation(summary = "Obtain token by Username and Password.")
  @SecurityRequirements
  @PostMapping("/token")
  public String authenticateAndGetToken(@RequestBody LoginDto authRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()));
    if (authentication.isAuthenticated()) {
      return jwtUtils.generateToken(authRequest.getUsername());
    } else {
      throw new UsernameNotFoundException("invalid user request !");
    }
  }
}
