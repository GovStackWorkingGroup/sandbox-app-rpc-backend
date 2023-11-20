package global.govstack.rpcbackend.controller;

import global.govstack.rpcbackend.dto.SignUpDto;
import global.govstack.rpcbackend.dto.UserDetailsDto;
import global.govstack.rpcbackend.service.UserService;
import global.govstack.rpcbackend.service.exception.user.UserExistsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "User")
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_APP')")
public class UserController {

  private final UserService userService;
  private final ModelMapper modelMapper;

  public UserController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/")
  @Operation(summary = "Get Logged User Info")
  public UserDetailsDto get(Principal principal) {
    var user = userService.loadUserByUsername(principal.getName());
    return modelMapper.map(user, UserDetailsDto.class);
  }

  @Operation(summary = "Register user.")
  @PreAuthorize("hasAuthority('ROLE_APP')")
  @PostMapping("/register/user")
  public UserDetailsDto addNewUser(@RequestBody SignUpDto signUpDto) {
    var user = userService.addNewUser(signUpDto, "ROLE_USER");
    return modelMapper.map(user, UserDetailsDto.class);
  }

  @Operation(summary = "Register application.")
  @PreAuthorize("hasAuthority('ROLE_APP')")
  @PostMapping("/register/app")
  public UserDetailsDto addNewAppUser(@RequestBody SignUpDto signUpDto) {
    var user = userService.addNewUser(signUpDto, "ROLE_APP");
    return modelMapper.map(user, UserDetailsDto.class);
  }

  @ExceptionHandler({UserExistsException.class})
  public ResponseEntity<String> handleException(UserExistsException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }
}
