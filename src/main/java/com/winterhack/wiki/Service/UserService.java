package com.winterhack.wiki.Service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Data.User.UpdateUserDTO;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Exception.User.CreateUserException;
import com.winterhack.wiki.Exception.User.DeleteUserException;
import com.winterhack.wiki.Exception.User.ReadUserException;
import com.winterhack.wiki.Exception.User.UpdateUserException;
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

  public UserEntity readUser(String username) throws ReadUserException {
    return userRepository
      .findByUsername(username)
      .orElseThrow(
        () -> new ReadUserException("사용자가 존재하지 않습니다")
      );
  }

  public void deleteUser(String username) throws DeleteUserException {
    userRepository.delete(
      userRepository
        .findByUsername(username)
        .orElseThrow(() -> new DeleteUserException("사용자가 존재하지 않습니다"))
    );
  }

  public void updateUser(String username, UpdateUserDTO updateUserDTO) throws UpdateUserException {
    if (!isUserExist(username)) {
      throw new UpdateUserException("사용자가 존재하지 않습니다");
    }

    UserEntity userEntity = userRepository.findByUsername(username).get();

    if (updateUserDTO.getEmail() != null) {
      userEntity.setEmail(updateUserDTO.getEmail());
    }

    if (updateUserDTO.getPassword() != null) {
      userEntity.setPassword(
        BCrypt.hashpw(updateUserDTO.getPassword(), BCrypt.gensalt())
      );
    }

    userRepository.save(userEntity);
  }

}
