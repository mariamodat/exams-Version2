package com.task.examstrial1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
   @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE ,CascadeType.PERSIST,CascadeType.DETACH, CascadeType.REFRESH})
   @JoinTable(name = "user_role" , joinColumns =
   @JoinColumn(name = "user_id",
   referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
   )
    private Set<RoleEntity> roles = new HashSet<>();
    @JsonIgnore
    private String password;
}
