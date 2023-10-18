package global.govstack.rpcbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "rpc_data")
public class RPCData {

  public RPCData() {}

  public RPCData(String tenant, String token, String dataKey, String dataValue, User user) {
    this.tenant = tenant;
    this.token = token;
    this.dataKey = dataKey;
    this.dataValue = dataValue;
    this.user = user;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private int id;

  @Column private String tenant;
  @Column private String token;
  @Column private String dataKey;

  @Lob
  @Column(columnDefinition = "text")
  private String dataValue;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
