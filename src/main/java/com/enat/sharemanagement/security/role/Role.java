package com.enat.sharemanagement.security.role;


import com.enat.sharemanagement.security.privilage.Privilege;
import com.enat.sharemanagement.utils.Auditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.enat.sharemanagement.security.user.User;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity(name = "roles")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Role extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    @JsonIgnoreProperties(value = {"roles","users"})
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "roles_privileges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges;
    @Column(unique = true, updatable = false)
    private String name;
}
