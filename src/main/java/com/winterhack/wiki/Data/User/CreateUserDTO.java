package com.winterhack.wiki.Data.User;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CreateUserDTO {
  
  @NotEmpty
  private String username;
  
  @NotEmpty
  private String password;
  
  @NotEmpty
  private String email;
  

}
