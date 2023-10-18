package global.govstack.rpcbackend.dto;

import lombok.Data;

@Data
public class SetDataDto {
  private String tenant;
  private String key;
  private String value;
}
