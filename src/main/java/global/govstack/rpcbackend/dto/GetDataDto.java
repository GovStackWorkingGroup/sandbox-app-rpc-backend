package global.govstack.rpcbackend.dto;

import lombok.Data;

@Data
public class GetDataDto {
  private String tenant;
  private String key;
}
