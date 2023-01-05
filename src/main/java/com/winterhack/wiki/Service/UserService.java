package com.winterhack.wiki.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Exception.CreateUserException;
import com.winterhack.wiki.Repository.UserRepository;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public boolean isUserExist(String username) {
    return userRepository.findByUsername(username).isPresent();
  }

  public void createUser(String username, String password, String email) throws CreateUserException {
    if (isUserExist(username)) {
      throw new CreateUserException("사용자명이 중복됩니다");
    }

    Set<String> authorities = new HashSet<>();
    authorities.add("USER");

    UserEntity userEntity = new UserEntity();

    userEntity.setUsername(username);
    userEntity.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
    userEntity.setEmail(email);

    userRepository.save(userEntity);
  }

}
