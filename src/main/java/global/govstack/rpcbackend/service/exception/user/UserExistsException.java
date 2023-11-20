package global.govstack.rpcbackend.service.exception.user;

public class UserExistsException extends RuntimeException {

  public UserExistsException(String message) {
    super(message);
  }
}
