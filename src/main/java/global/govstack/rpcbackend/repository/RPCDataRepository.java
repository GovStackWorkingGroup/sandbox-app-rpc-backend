package global.govstack.rpcbackend.repository;

import global.govstack.rpcbackend.model.RPCData;
import global.govstack.rpcbackend.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RPCDataRepository extends JpaRepository<RPCData, Long> {
  Optional<RPCData> findByUserAndDataKeyAndTenant(User user, String key, String tenant);

  @Modifying
  @Query("update RPCData r set r.token = null where r.user = ?1 AND tenant = ?2")
  void resetTokenByUserAndTenant(User user, String tenant);
}
