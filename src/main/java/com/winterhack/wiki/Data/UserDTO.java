package com.winterhack.wiki.Data;

import java.util.Set;

import lombok.Data;

@Data
public class UserDTO {
  
  private String username;
  private String email;
  private String password;
  private String lastAccessAddr;
  private Set<String> autorities;

}
