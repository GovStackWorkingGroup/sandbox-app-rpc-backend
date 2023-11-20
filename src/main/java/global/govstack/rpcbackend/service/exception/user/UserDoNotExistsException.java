package global.govstack.rpcbackend.service.exception.user;

public class UserDoNotExistsException extends RuntimeException {

  public UserDoNotExistsException(String message) {
    super(message);
  }
}
