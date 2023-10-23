package global.govstack.rpcbackend.controller;

import global.govstack.rpcbackend.dto.GetDataDto;
import global.govstack.rpcbackend.dto.RpcDataDto;
import global.govstack.rpcbackend.dto.SetDataDto;
import global.govstack.rpcbackend.service.RPCDataService;
import global.govstack.rpcbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rpc-data")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class RPCDataController {

  private UserService userService;
  private RPCDataService rpcDataService;

  public RPCDataController(UserService userService, RPCDataService rpcDataService) {
    this.userService = userService;
    this.rpcDataService = rpcDataService;
  }

  @PostMapping("/data")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  @Operation(summary = "Get Stored data by tenant & data-key")
  public RpcDataDto get(Principal principal, @RequestBody GetDataDto getDataDto) {
    var user = userService.loadUserByUsername(principal.getName());
    return rpcDataService.getData(user, getDataDto.getKey(), getDataDto.getTenant());
  }

  @PutMapping("/data")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  @Operation(summary = "Store data by tenant & data-key")
  public RpcDataDto put(
      Principal principal,
      @Parameter(hidden = true) @RequestHeader(name = "Authorization") String authorisation,
      @RequestBody SetDataDto setDataDto) {
    var user = userService.loadUserByUsername(principal.getName());
    return rpcDataService.setData(
        user, authorisation, setDataDto.getTenant(), setDataDto.getKey(), setDataDto.getValue());
  }

  @PatchMapping("/data")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  @Operation(
      summary =
          "Force Store data by tenant & data-key (Useful when trying to override data from different session!)")
  public RpcDataDto set(
      Principal principal,
      @Parameter(hidden = true) @RequestHeader(name = "Authorization") String authorisation,
      @RequestBody SetDataDto setDataDto) {
    var user = userService.loadUserByUsername(principal.getName());
    return rpcDataService.setData(
        user,
        authorisation,
        setDataDto.getTenant(),
        setDataDto.getKey(),
        setDataDto.getValue(),
        true);
  }

  @DeleteMapping("/session")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  @Operation(
      summary =
          "Invalidate storage session. Every other session will be able to override data for that tenant + user + key")
  public ResponseEntity flushStorageSessions(Principal principal, @RequestParam String tenant) {
    var user = userService.loadUserByUsername(principal.getName());
    rpcDataService.flushStorageSessions(user, tenant);
    return new ResponseEntity(HttpStatus.RESET_CONTENT);
  }

  @ExceptionHandler({SecurityException.class})
  public ResponseEntity<String> handleException() {
    return new ResponseEntity<>(
        "Data invalidation attempt! Please logout or force push your changes! This will invalidate all previous sessions!",
        HttpStatus.BAD_REQUEST);
  }
}
