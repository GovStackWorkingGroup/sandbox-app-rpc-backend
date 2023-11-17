package global.govstack.rpcbackend.dto;

import java.util.Set;
import lombok.Data;

@Data
public class UserDetailsDto {
  private Long id;
  private String name;
  private String username;
  private Set<RoleDto> roles;
}
