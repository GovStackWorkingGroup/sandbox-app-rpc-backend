package global.govstack.rpcbackend.service.exception.rpc;

public class DataNotFoundException extends RuntimeException {

  public DataNotFoundException(String message) {
    super(message);
  }
}
