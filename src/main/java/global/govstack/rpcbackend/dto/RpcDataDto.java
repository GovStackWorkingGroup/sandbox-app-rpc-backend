package global.govstack.rpcbackend.dto;

import global.govstack.rpcbackend.model.RPCData;
import lombok.Data;

@Data
public class RpcDataDto {
  public RpcDataDto() {}

  public RpcDataDto(RPCData data) {
    this.key = data.getDataKey();
    this.value = data.getDataValue();
    this.tenant = data.getTenant();
  }

  private String tenant;
  private String key;
  private String value;
}
