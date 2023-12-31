package global.govstack.rpcbackend.model;

import jakarta.persistence.*;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(
    name = "users",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private String username;
  private String password;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Set<Role> roles;
}
