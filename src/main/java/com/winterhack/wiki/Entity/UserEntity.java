package com.winterhack.wiki.Entity;

import java.util.Set;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false, length = 500, unique = true)
  private String username;

  @Column(nullable = false, length = 500)
  private String password;

  @Column(nullable = false, length = 500)
  private String email;

  @Column(nullable = true, length = 100)
  private String lastAccessAddr;

  @Column(nullable = false, length = 100)
  @ElementCollection(targetClass = String.class)
  private Set<String> authorities;

}
