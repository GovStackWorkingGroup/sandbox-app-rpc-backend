package global.govstack.rpcbackend.controller;

import global.govstack.rpcbackend.dto.LoginDto;
import global.govstack.rpcbackend.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
public class AuthController {

  private final JwtUtils jwtUtils;

  private final AuthenticationManager authenticationManager;

  public AuthController(JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
    this.jwtUtils = jwtUtils;
    this.authenticationManager = authenticationManager;
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
