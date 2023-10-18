package global.govstack.rpcbackend.dto;

import lombok.Data;

@Data
public class SignUpDto {
  private String name;
  private String username;
  private String password;
}
