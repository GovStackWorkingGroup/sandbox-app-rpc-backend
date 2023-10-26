package global.govstack.rpcbackend.service;

import global.govstack.rpcbackend.dto.RpcDataDto;
import global.govstack.rpcbackend.model.RPCData;
import global.govstack.rpcbackend.model.User;
import global.govstack.rpcbackend.repository.RPCDataRepository;
import global.govstack.rpcbackend.util.JwtUtils;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RPCDataService {

  private RPCDataRepository rpcDataRepository;
  private JwtUtils jwtUtils;

  public RPCDataService(RPCDataRepository rpcDataRepository, JwtUtils jwtUtils) {
    this.rpcDataRepository = rpcDataRepository;
    this.jwtUtils = jwtUtils;
  }

  public RpcDataDto setData(
      User user, String authorisation, String tenant, String key, String value) {
    return setData(user, authorisation, tenant, key, value, false);
  }

  public RpcDataDto setData(
      User user, String authorisation, String tenant, String key, String value, boolean forced) {
    var dataRecord = rpcDataRepository.findByUserAndDataKeyAndTenant(user, key, tenant);
    var token = jwtUtils.extractTokenFromHeader(authorisation);

    var rpcData =
        dataRecord
            .map(
                data -> {
                  var dt = data.getToken();
                  if (forced
                      || dt == null
                      || dt.isEmpty()
                      || Objects.equals(dt, token)
                      || jwtUtils.isTokenExpired(dt)) {
                    data.setDataValue(value);
                    data.setToken(token);
                  } else {
                    throw new SecurityException("Data invalidation attempt!");
                  }
                  return data;
                })
            .orElse(new RPCData(tenant, token, key, value, user));

    return new RpcDataDto(rpcDataRepository.save(rpcData));
  }

  public RpcDataDto getData(User user, String key, String tenant) {
    return new RpcDataDto(rpcDataRepository.findByUserAndDataKeyAndTenant(user, key, tenant).get());
  }

  @Transactional
  public void flushStorageSessions(User user, String tenant) {
    rpcDataRepository.resetTokenByUserAndTenant(user, tenant);
  }
}
