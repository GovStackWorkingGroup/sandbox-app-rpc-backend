package global.govstack.rpcbackend.service.exception.role;

public class RoleDoNotExistsException extends RuntimeException {

  public RoleDoNotExistsException(String message) {
    super(message);
  }
}
