package global.govstack.rpcbackend.controller;

import global.govstack.rpcbackend.dto.UserDetailsDto;
import global.govstack.rpcbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.security.Principal;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
public class UserController {

  private UserService userService;

  private final ModelMapper modelMapper;

  public UserController(UserService userService, ModelMapper modelMapper) {
    this.userService = userService;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  @Operation(summary = "Get Logged User Info")
  public UserDetailsDto get(Principal principal) {
    var user = userService.loadUserByUsername(principal.getName());
    return modelMapper.map(user, UserDetailsDto.class);
  }
}
