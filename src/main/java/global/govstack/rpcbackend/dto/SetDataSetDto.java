package global.govstack.rpcbackend.dto;

import java.util.Map;
import lombok.Data;

@Data
public class SetDataSetDto {
  private String tenant;
  private Map<String, String> keyValuePairs;
}
