package com.winterhack.wiki.Data;

import java.util.Set;

import com.winterhack.wiki.Entity.UserEntity;

import lombok.Data;

@Data
public class ReadUserDTO {
  
  private String username;
  private String email;
  private String lastAccessAddr;
  private Set<String> authorities;

  public ReadUserDTO(UserEntity user) {
    username = user.getUsername();
    email = user.getEmail();
    lastAccessAddr = user.getLastAccessAddr();
    authorities = user.getAuthorities();
  }

}
