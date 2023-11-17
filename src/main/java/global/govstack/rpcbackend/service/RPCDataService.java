package global.govstack.rpcbackend.service;

import global.govstack.rpcbackend.dto.RpcDataDto;
import global.govstack.rpcbackend.model.RPCData;
import global.govstack.rpcbackend.model.User;
import global.govstack.rpcbackend.repository.RPCDataRepository;
import global.govstack.rpcbackend.util.JwtUtils;
import java.util.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RPCDataService {

  private RPCDataRepository rpcDataRepository;
  private JwtUtils jwtUtils;

  private final ModelMapper modelMapper;

  public RPCDataService(
      RPCDataRepository rpcDataRepository, JwtUtils jwtUtils, ModelMapper modelMapper) {
    this.rpcDataRepository = rpcDataRepository;
    this.jwtUtils = jwtUtils;
    this.modelMapper = modelMapper;
  }

  @Transactional
  public RpcDataDto setData(
      User user, String authorisation, String tenant, String key, String value) {
    return setData(user, authorisation, tenant, key, value, false);
  }

  @Transactional
  public RpcDataDto setData(
      User user, String authorisation, String tenant, String key, String value, boolean forced) {
    var dataRecord = rpcDataRepository.findByUserAndDataKeyAndTenant(user, key, tenant);
    var token = jwtUtils.extractTokenFromHeader(authorisation);
    var rpcData = processRPCData(dataRecord, user, tenant, token, key, value, forced);
    return new RpcDataDto(rpcDataRepository.save(rpcData));
  }

  @Transactional
  public List<RpcDataDto> setDataSet(
      User user, String authorisation, String tenant, Map<String, String> keyValuePairs) {
    return setDataSet(user, authorisation, tenant, keyValuePairs, false);
  }

  @Transactional
  public List<RpcDataDto> setDataSet(
      User user,
      String authorisation,
      String tenant,
      Map<String, String> keyValuePairs,
      boolean forced) {
    var dataRecords =
        rpcDataRepository.findByUserAndDataKeyInAndTenant(user, keyValuePairs.keySet(), tenant);
    var token = jwtUtils.extractTokenFromHeader(authorisation);

    var dataSet =
        keyValuePairs.entrySet().stream()
            .map(
                e -> {
                  var dataRecord =
                      dataRecords.stream()
                          .filter(rec -> Objects.equals(rec.getDataKey(), e.getKey()))
                          .findAny();
                  return processRPCData(
                      dataRecord, user, tenant, token, e.getKey(), e.getValue(), forced);
                })
            .toList();
    return modelMapper.map(
        rpcDataRepository.saveAll(dataSet), new TypeToken<List<RpcDataDto>>() {}.getType());
  }

  private RPCData processRPCData(
      Optional<RPCData> dataRecord,
      User user,
      String tenant,
      String token,
      String key,
      String value,
      boolean forced) {
    return dataRecord
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
  }

  public RpcDataDto getData(User user, String key, String tenant) {
    return new RpcDataDto(rpcDataRepository.findByUserAndDataKeyAndTenant(user, key, tenant).get());
  }

  public List<RpcDataDto> getData(User user, Set<String> keys, String tenant) {
    var res = rpcDataRepository.findByUserAndDataKeyInAndTenant(user, keys, tenant);
    return modelMapper.map(res, new TypeToken<List<RpcDataDto>>() {}.getType());
  }

  public List<RpcDataDto> getData(User user, String tenant) {
    var res = rpcDataRepository.findByUserAndTenant(user, tenant);
    return modelMapper.map(res, new TypeToken<List<RpcDataDto>>() {}.getType());
  }

  @Transactional
  public void flushStorageSessions(User user, String tenant) {
    rpcDataRepository.resetTokenByUserAndTenant(user, tenant);
  }
}
