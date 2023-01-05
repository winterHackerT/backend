package com.winterhack.wiki.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public void addNewUser(String username, String password, String email) {
    Set<String> authorities = new HashSet<>();
    authorities.add("USER");

    UserEntity userEntity = new UserEntity();

    userEntity.setUsername(username);
    userEntity.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    userEntity.setEmail(email);

    userRepository.save(userEntity);
  }

}
