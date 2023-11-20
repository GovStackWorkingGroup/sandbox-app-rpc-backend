package global.govstack.rpcbackend.dto;

import java.util.Set;
import lombok.Data;

@Data
public class GetDataSetDto {
  private String tenant;
  private Set<String> keys;
}
